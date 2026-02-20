package com.evently.booking.order.data;

import com.evently.booking.order.Order;
import com.evently.booking.order.entities.OrderDetails;
import com.evently.booking.ticket.entities.TicketListItem;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class OrderMapper {
    public OrderDetails toDto(Order order, List<TicketListItem> listItems) {
        if (order == null) {
            return null;
        }
        if (Objects.isNull(order.getTransactionId())) {
            order.setTransactionId("NOT_APPLICABLE");
        }
        return new OrderDetails(
                order.getId(),
                order.getNumber(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getExpirationTime(),
                order.getCreatedAt(),
                order.getTransactionId(),
                listItems != null ? listItems : Collections.emptyList()
        );
    }
}