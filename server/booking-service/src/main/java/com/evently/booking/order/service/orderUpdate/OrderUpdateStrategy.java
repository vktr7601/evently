package com.evently.booking.order.service.orderUpdate;

import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;

public interface OrderUpdateStrategy {
    boolean supports(OrderStatus status);

    void update(Order order);
}