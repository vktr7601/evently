package com.evently.booking.order.controller;

import com.evently.booking.order.dto.FinishOrderRequest;
import com.evently.booking.order.dto.OrderDetails;
import com.evently.booking.order.dto.OrderListItemDto;
import com.evently.booking.order.dto.OrderRequest;
import com.evently.booking.order.service.OrderService;
import constants.ApplicationHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> syncOrderItems(@RequestHeader(ApplicationHeaders.USER_ID) Long userId,
                                            @RequestBody OrderRequest orderRequest) {
        orderService.addTicketsToOrder(userId, orderRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<OrderDetails> getActive(@RequestHeader(ApplicationHeaders.USER_ID) Long userId) {
        var result = orderService.getActiveUserOrder(userId);
        return ResponseEntity.ok(orderService.getActiveUserOrder(userId));
    }

    @DeleteMapping("/active")
    public ResponseEntity<Void> discardActiveOrder(@RequestHeader(ApplicationHeaders.USER_ID) Long userId) {
        orderService.cancelActiveOrder(userId);
        return ResponseEntity.noContent().build(); // 204 No Content is
        // standard for successful DELETE
    }

    @GetMapping
    public ResponseEntity<List<OrderListItemDto>> getUserOrders(@RequestHeader(ApplicationHeaders.USER_ID) Long userId) {
        var orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/details/{number}")
    public ResponseEntity<OrderDetails> getOrderDetails(@RequestHeader(ApplicationHeaders.USER_ID) Long userId, @PathVariable UUID number) {
        var orderDetails = orderService.getOrderDetails(userId, number);
        return ResponseEntity.ok(orderDetails);
    }


    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestHeader(ApplicationHeaders.USER_ID) Long userId, @RequestBody FinishOrderRequest finishOrderRequest) {
        orderService.finishActiveUserOrder(userId, finishOrderRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderNumber}/refund-eligibility")
    public ResponseEntity<?> getRefundEligibility(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable UUID orderNumber) {

        boolean eligible = orderService.isWithinRefundPeriod(userId,
                orderNumber);

        return ResponseEntity.ok(eligible);
    }
}