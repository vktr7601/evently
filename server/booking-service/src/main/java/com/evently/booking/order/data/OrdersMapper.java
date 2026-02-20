package com.evently.booking.order.data;

import com.evently.booking.order.Order;
import com.evently.booking.order.entities.OrderDetails;
import com.evently.booking.ticket.entities.TicketListItem;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrdersMapper {
    public OrderDetails toDto(Order order, List<TicketListItem> listItems) {
        if (order == null) {
            return null;
        }

        return new OrderDetails(
                order.getId(),
                order.getNumber(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getExpirationTime(),
                order.getCreatedAt(),
                listItems != null ? listItems : Collections.emptyList()
        );
    }
}