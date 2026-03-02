package com.evently.booking.order.service.orderUpdate;

import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.order.repository.OrderRepository;
import com.evently.booking.ticket.model.TicketStatus;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderRefundedStrategy extends OrderUpdateBaseStrategy {
    public OrderRefundedStrategy(TicketService ticketService,
                                 ObjectMapper objectMapper,
                                 OrderRepository orderRepository) {
        super(ticketService, objectMapper, orderRepository);
    }

    @Override
    public boolean supports(OrderStatus status) {
        return status == OrderStatus.REFUNDED;
    }

    @Override
    public void update(Order order) {
        order.getTickets().forEach(ticket -> {
            ticket.setStatus(TicketStatus.REFUNDED);
        });
        writeAudit(order, TicketStatus.REFUNDED);
        order.getTickets().forEach(ticket -> {
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setUserId(null);
            ticket.setOrder(null);
        });

        order.setStatus(OrderStatus.REFUNDED);
        order.setActive(false);
        orderRepository.save(order);
    }
}