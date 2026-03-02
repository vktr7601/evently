package com.evently.booking.order.service.orderUpdate;

import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.order.repository.OrderRepository;
import com.evently.booking.ticket.model.TicketStatus;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmedStrategy extends OrderUpdateBaseStrategy {

    public OrderConfirmedStrategy(TicketService ticketService,
                                  ObjectMapper objectMapper,
                                  OrderRepository orderRepository) {
        super(ticketService, objectMapper, orderRepository);
    }

    @Override
    public boolean supports(OrderStatus status) {
        return status == OrderStatus.CONFIRMED;
    }

    @Override
    public void update(Order order) {
        order.getTickets().forEach(ticket -> {
            ticket.setStatus(TicketStatus.BOOKED);
        });
//        ticketService.finalizeOrder(order.getId());

        order.setStatus(OrderStatus.CONFIRMED);
        order.setActive(false);
////        order.setTotalPrice(order.getTickets().stream()
////                .map(Ticket::getPrice)
////                .reduce(BigDecimal.ZERO, BigDecimal::add));
//        order.setTransactionId(paymentResponse.getTransactionId());
        orderRepository.save(order);
    }
}