package com.evently.booking.order.service.orderUpdate;

import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.order.repository.OrderRepository;
import com.evently.booking.ticket.model.TicketStatus;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
 class ExpiredOrderStrategy extends OrderUpdateBaseStrategy {
    public ExpiredOrderStrategy(TicketService ticketService,
                                ObjectMapper objectMapper,
                                OrderRepository orderRepository) {
        super(ticketService, objectMapper, orderRepository);
    }

    @Override
    public boolean supports(OrderStatus status) {
        return status == OrderStatus.EXPIRED;
    }

    @Override
    public void update(Order order) {
        writeAudit(order, TicketStatus.EXPIRED);
        order.setStatus(OrderStatus.EXPIRED);
        order.setActive(false);
        order.getTickets().forEach(ticket -> {
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setUserId(null);
            ticket.setOrder(null);
        });

        orderRepository.save(order);
    }
}