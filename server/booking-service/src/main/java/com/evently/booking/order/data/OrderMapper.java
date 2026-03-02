package com.evently.booking.order.data;

import com.evently.booking.order.dto.OrderDetails;
import com.evently.booking.order.model.Order;
import com.evently.booking.ticket.dto.TicketListItem;
import events.order.OrderPaymentSucceededEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderMapper {
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
                order.getTransactionId(),
                listItems != null ? listItems : Collections.emptyList());
    }

    public OrderPaymentSucceededEvent toOrderPaymentSucceededEvent(Order order) {
        OrderPaymentSucceededEvent orderPaymentSucceededEvent =
                new OrderPaymentSucceededEvent();
        orderPaymentSucceededEvent.setOrderNumber(order.getNumber().toString());
        orderPaymentSucceededEvent.setTotalAmount(order.getTotalPrice());
        // orderPaymentSucceededEvent.setReceiptUrl(order.getReceiptUrl());
        orderPaymentSucceededEvent.setUserId(order.getUserId());
        return orderPaymentSucceededEvent;
    }
}