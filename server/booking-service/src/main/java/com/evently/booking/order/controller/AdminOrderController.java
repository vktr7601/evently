package com.evently.booking.order.controller;

import com.evently.booking.order.dto.OrderListItemDto;
import com.evently.booking.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderListItemDto>> getOrders() {
        var orders = orderService.getSystemOrders();
        return ResponseEntity.ok(orders);
    }
}