package com.evently.booking.refund.service;

import com.evently.booking.infrastructure.clients.paymentService.PaymentServiceClient;
import com.evently.booking.infrastructure.exceptions.OrderNotRefundableException;
import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.order.service.OrderService;
import com.evently.booking.refund.dto.IneligibleTicket;
import com.evently.booking.refund.dto.OrderRefundEligibility;
import com.evently.booking.refund.dto.RefundEligibility;
import com.evently.booking.ticket.model.Ticket;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.payment.refund.RefundRequest;
import dto.payment.refund.RefundResponse;
import dto.payment.refund.RefundType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class RefundService {
    private final OrderService orderService;
    private final PaymentServiceClient paymentServiceClient;
    private final ObjectMapper objectMapper;
    private final TicketService ticketService;

    @Transactional
    public void refundOrder(UUID orderNumber, long userId) throws OrderNotRefundableException {
        OrderRefundEligibility orderRefundEligibility =
                getOrderRefundEligibility(userId, orderNumber);
        if (!orderRefundEligibility.isEligible()) {
            throw new OrderNotRefundableException(orderNumber);
        }

        Order order = orderService.findByOrderNumberAndUserId(orderNumber,
                userId);

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setAmount(order.getTotalPrice());
        //  refundRequest.setTransactionId(order.getTransactionId());
        refundRequest.setOrderNumber(order.getNumber().toString());
        refundRequest.setRequestedBy(userId);
        refundRequest.setRefundType(RefundType.FULL_ORDER);

        ResponseEntity<RefundResponse> response =
                paymentServiceClient.processRefund(refundRequest);

        RefundResponse body = response.getBody();

//        if (body.isSuccess()) {
//            order.setRefundId(body.getRefundTransactionId());
//            order.setRefundTime(LocalDateTime.now());
//            orderService.updateOrderDetails(order, OrderStatus.REFUNDED);
//
//            order.getTickets().forEach(ticket -> {
//                ticket.setStatus(TicketStatus.REFUNDED);
//                ticket.setUserId(null);
//                ticket.setOrder(null);
//            });
//
//            order.setStatus(OrderStatus.REFUNDED);
//            order.setTransactionId(body.getRefundTransactionId());
//
//            try {
//                order.setAudit(objectMapper.writeValueAsString(order
//                .getTickets()));
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//
//            orderService.save(order);
//            log.info("Order {} refunded successfully, refund transaction: {}",
//                    orderNumber, body.getRefundTransactionId());
//        } else {
//            //  throw new ProcessOrderException(body);
//        }
    }

    public RefundResponse refundTicket(long ticketId, long userId) {
        Ticket ticket = ticketService.findTicketById(ticketId);

        if (!isWithinRefundWindow(ticket)) {
//            throw new TicketNotRefundableException(ticketId);
        }

        Order order = ticket.getOrder();
        List<Ticket> tickets = order.getTickets();
        boolean isOnlyTicket = tickets.size() == 1;

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderNumber(order.getNumber().toString());
        // refundRequest.setTransactionId(order.getTransactionId());
        refundRequest.setAmount(ticket.getPrice());
        refundRequest.setRequestedBy(userId);
        refundRequest.setRefundType(isOnlyTicket
                ? RefundType.FULL_ORDER
                : RefundType.PARTIAL_TICKET);

        RefundResponse response =
                paymentServiceClient.processRefund(refundRequest).getBody();

        if (!response.isSuccess()) {
            //throw new ProcessRefundException(response.getMessage());
            System.out.println();
        }
        orderService.updateOrderDetails(order, OrderStatus.REFUNDED);
        return response;
    }

    private boolean isWithinRefundWindow(Ticket ticket) {
        LocalDateTime refundDeadline = ticket.getEventStartTime().minusDays(1);
        return LocalDateTime.now().isBefore(refundDeadline);
    }

    public RefundEligibility getTicketRefundEligibility(long ticketId) {
        Ticket ticket = ticketService.findTicketById(ticketId);

        boolean isWithinRefundWindow = isWithinRefundWindow(ticket);

        return isWithinRefundWindow ? new RefundEligibility(true, "") :
                new RefundEligibility(false, "Ticket not in refundable period");
    }

    public OrderRefundEligibility getOrderRefundEligibility(Long userId,
                                                            UUID orderNumber) {
        Order order = orderService.findByOrderNumberAndUserId(orderNumber,
                userId);
        List<Ticket> tickets = order.getTickets();

        List<IneligibleTicket> ineligible = order.getTickets().stream()
                .filter(ticket -> !isWithinRefundWindow(ticket))
                .map(ticket -> new IneligibleTicket(ticket.getNumber(),
                        "Refund window has expired"))
                .toList();

        boolean eligible = ineligible.isEmpty();
        String reason = eligible ? null : "Some tickets are not eligible for " +
                "refund";

        return new OrderRefundEligibility(eligible, reason, ineligible);
    }
}