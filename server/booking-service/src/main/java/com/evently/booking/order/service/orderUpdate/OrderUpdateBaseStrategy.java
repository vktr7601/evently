package com.evently.booking.order.service.orderUpdate;

import com.evently.booking.order.model.Order;
import com.evently.booking.order.repository.OrderRepository;
import com.evently.booking.ticket.dto.TicketListItem;
import com.evently.booking.ticket.model.TicketStatus;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
abstract class OrderUpdateBaseStrategy implements OrderUpdateStrategy {
    protected final TicketService ticketService;
    protected final ObjectMapper objectMapper;
    protected final OrderRepository orderRepository;

    protected void writeAudit(Order order, TicketStatus ticketStatus) {
        List<TicketListItem> items = ticketService.getTicketsByOrder(order);
        items.forEach(x -> x.setStatus(ticketStatus));
        try {
            order.setAudit(objectMapper.writeValueAsString(items));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to write audit for order "
                    + order.getId(), e);
        }
    }
}