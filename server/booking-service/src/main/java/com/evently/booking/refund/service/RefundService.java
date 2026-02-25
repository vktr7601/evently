package com.evently.booking.refund.service;

import com.evently.booking.order.model.Order;
import com.evently.booking.order.service.OrderService;
import com.evently.booking.infrastructure.clients.paymentService.PaymentServiceClient;
import com.evently.booking.ticket.model.Ticket;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public boolean isOrderRefundable(Long userId, UUID number) {
        Order order = orderService.findByOrderNumberAndUserId(number, userId);

        List<Ticket> tickets = order.getTickets();

        boolean canRefund = true;
        for (Ticket ticket : tickets) {
            if (ticket.getEventStartTime().isBefore(LocalDateTime.now().plusHours(2))) {
                canRefund = false;
                break;
            }
        }

        return canRefund;
    }

//    @Transactional
//    public void refundOrder(long number, long userId) throws
//    OrderNotRefundableException {
//        if (!isOrderRefundable(userId, number)) {
//            throw new OrderNotRefundableException(number);
//        }
//
//
//        Order order = orderService.findByOrderNumberAndUserId(number, userId);
//
//        List<Ticket> tickets = order.getTickets();
//
//        List<TicketListItem> ticketListItems = orderService
//        .resolveOrderItems(order);
//        for (TicketListItem item : ticketListItems) {
//            item.setStatus(TicketStatus.REFUNDED);
//        }
//
//
//        try {
//            String json = objectMapper.writeValueAsString(ticketListItems);
//            order.setAudit(json);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        RefundRequest refundRequest = new RefundRequest();
//        refundRequest.setAmount(order.getTotalPrice());
//        refundRequest.setTransactionId(order.getTransactionId());
//        ResponseEntity<PaymentServiceResponse> response =
//        paymentServiceClient.processRefund(refundRequest);
//        if (response.getBody().isSuccess()) {
//            for (Ticket ticket : tickets) {
//                ticket.setStatus(TicketStatus.AVAILABLE);
//                ticket.setUserId(null);
//                ticket.setOrder(null);
//            }
//            order.setStatus(OrderStatus.REFUNDED);
//            order.setTransactionId(response.getBody().toString());
//            orderService.save(order);
//        } else {
//            throw new ProcessOrderException(response.getBody());
//        }
//        System.out.println();
//
//    }


//    public void refundTicketRequest(Long userId, long ticketId) throws OrderNotRefundableException {
//        Ticket ticket = ticketService.findTicketById(ticketId);
//        if (isTicketRefundable(ticket)) {
//            Order order = ticket.getOrder();
//            if(order.getTickets().size() == 1){
//                refundOrder(order.getNumber(), userId);
//            }
////            if (ticket.getDateTime().isBefore(LocalDateTime.now().plusHours(2))) {
////                throw new OrderNotRefundableException(ticketId);
////            }
////
////            var order = ticket.getOrder();
////
////            List<Ticket> tickets = order.getTickets();
////            if (tickets.size() == 1) {
////                order.setStatus(OrderStatus.REFUNDED);
////                orderService.save(order);
////            }
//        }
//    }

    boolean isTicketRefundable(Ticket ticket) {
        return !ticket.getEventStartTime().isBefore(LocalDateTime.now().plusHours(2));
    }
}