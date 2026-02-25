package com.evently.booking.order.service;

import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderExpirationScheduler {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Scheduled(cron = "0 * * * * *")
    public void expireOverdueOrders() {
        List<Order> expiredOrders =
                orderRepository.findExpiredPendingOrders(LocalDateTime.now());

        if (expiredOrders.isEmpty()) {
            return;
        }

        for (Order order : expiredOrders) {
            orderService.updateOrderDetails(order, OrderStatus.EXPIRED);
        }

        log.info("Expired {} overdue orders", expiredOrders.size());
    }
}