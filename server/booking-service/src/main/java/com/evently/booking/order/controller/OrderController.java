package com.evently.booking.order.controller;

import com.evently.booking.order.dto.FinishOrderRequest;
import com.evently.booking.order.dto.OrderDetails;
import com.evently.booking.order.dto.OrderListItemDto;
import com.evently.booking.order.dto.OrderRequest;
import com.evently.booking.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static constants.ApplicationHeaders.USER_EMAIL;
import static constants.ApplicationHeaders.USER_ID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> syncOrderItems(@RequestHeader(USER_ID) Long userId,
                                            @RequestBody OrderRequest orderRequest) {
        orderService.addTicketsToOrder(userId, orderRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/active/tickets")
    public ResponseEntity<OrderDetails> removeTickets(@RequestHeader(USER_ID) Long userId, @RequestBody OrderRequest orderRequest) {
        OrderDetails result = orderService.releaseTickets(userId, orderRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/active")
    public ResponseEntity<OrderDetails> getActive(@RequestHeader(USER_ID) Long userId) {
        var result = orderService.getActiveUserOrder(userId);
        return ResponseEntity.ok(orderService.getActiveUserOrder(userId));
    }

    @DeleteMapping("/active")
    public ResponseEntity<Void> discardActiveOrder(@RequestHeader(USER_ID) Long userId) {
        orderService.cancelActiveOrder(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderListItemDto>> getUserOrders(@RequestHeader(USER_ID) Long userId) {
        var orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/details/{number}")
    public ResponseEntity<OrderDetails> getOrderDetails(@RequestHeader(USER_ID) Long userId, @PathVariable UUID number) {
        var orderDetails = orderService.getOrderDetails(userId, number);
        return ResponseEntity.ok(orderDetails);
    }


    @PostMapping("/complete")
    public ResponseEntity<?> complete(@RequestHeader(USER_ID) Long userId,
                                      @RequestHeader(USER_EMAIL) String userEmail,
                                      @RequestBody FinishOrderRequest finishOrderRequest) {
        orderService.completeOrder(userEmail, userId,
                finishOrderRequest);
        return ResponseEntity.noContent().build();
    }
}