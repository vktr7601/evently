package com.evently.booking.refund.service;

import com.evently.booking.infrastructure.clients.paymentService.PaymentServiceClient;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentServiceResponse;
import com.evently.booking.infrastructure.clients.paymentService.data.RefundRequest;
import com.evently.booking.infrastructure.clients.paymentService.data.RefundResponse;
import com.evently.booking.infrastructure.clients.paymentService.data.RefundServiceResponse;
import com.evently.booking.infrastructure.exceptions.OrderNotRefundableException;
import com.evently.booking.infrastructure.exceptions.ProcessOrderException;
import com.evently.booking.infrastructure.exceptions.TicketNotRefundableException;
import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.order.service.OrderService;
import com.evently.booking.refund.dto.IneligibleTicket;
import com.evently.booking.refund.dto.OrderRefundEligibility;
import com.evently.booking.refund.dto.RefundEligibility;
import com.evently.booking.ticket.model.Ticket;
import com.evently.booking.ticket.model.TicketStatus;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefundService {
    private final OrderService orderService;
    private final PaymentServiceClient paymentServiceClient;
    private final ObjectMapper objectMapper;
    private final TicketService ticketService;

//    public boolean isOrderRefundable(Long userId, UUID number) {
//        Order order = orderService.findByOrderNumberAndUserId(number, userId);
//
//        List<Ticket> tickets = order.getTickets();
//
//        boolean canRefund = true;
//        for (Ticket ticket : tickets) {
//            if (ticket.getEventStartTime().isBefore(LocalDateTime.now()
//            .plusHours(2))) {
//                canRefund = false;
//                break;
//            }
//        }
//
//        return canRefund;
//    }


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
        refundRequest.setTransactionId(order.getTransactionId());
        refundRequest.setReason("User requested");

        ResponseEntity<RefundResponse> response =
                paymentServiceClient.processRefund(refundRequest);

        RefundResponse body = response.getBody();
//        if (body == null) {
//            throw new ProcessOrderException("Empty response from payment " +
//                    "service");
//        }

        if (body.isSuccess()) {
            order.getTickets().forEach(ticket -> {
                ticket.setStatus(TicketStatus.REFUNDED);
                ticket.setUserId(null);
                ticket.setOrder(null);
            });

            order.setStatus(OrderStatus.REFUNDED);
            order.setRefundTransactionId(body.getRefundTransactionId());

            try {
                order.setAudit(objectMapper.writeValueAsString(order.getTickets()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            orderService.save(order);
            log.info("Order {} refunded successfully, refund transaction: {}",
                    orderNumber, body.getRefundTransactionId());
        } else {
            throw new ProcessOrderException(body);
        }
    }
//    public void refundTicketRequest(Long userId, long ticketId) throws
//    OrderNotRefundableException {
//        Ticket ticket = ticketService.findTicketById(ticketId);
//        if (isTicketRefundable(ticket)) {
//            Order order = ticket.getOrder();
//            if(order.getTickets().size() == 1){
//                refundOrder(order.getNumber(), userId);
//            }

    /// /            if (ticket.getDateTime().isBefore(LocalDateTime.now()
    /// .plusHours(2))) {
    /// /                throw new OrderNotRefundableException(ticketId);
    /// /            }
    /// /
    /// /            var order = ticket.getOrder();
    /// /
    /// /            List<Ticket> tickets = order.getTickets();
    /// /            if (tickets.size() == 1) {
    /// /                order.setStatus(OrderStatus.REFUNDED);
    /// /                orderService.save(order);
    /// /            }
//        }
//    }
    public RefundServiceResponse refundTicket(long ticketId) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        if (!isWithinRefundWindow(ticket)) {
            throw new TicketNotRefundableException();
        }

        var order = ticket.getOrder();

        List<Ticket> tickets = order.getTickets();
        if (tickets.size() == 1) {
            order.setStatus(OrderStatus.REFUNDED);
            orderService.save(order);
        }
        return null;
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