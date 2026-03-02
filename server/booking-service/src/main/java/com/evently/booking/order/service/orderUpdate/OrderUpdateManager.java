package com.evently.booking.order.service.orderUpdate;

import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderUpdateManager {
    private final List<OrderUpdateStrategy> strategies;

    @Transactional
    public void update(Order order, OrderStatus status) {
        strategies.stream()
                .filter(s -> s.supports(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No strategy found for status: " + status))
                .update(order);
    }
}