package com.evently.booking.order;

import com.evently.booking.order.entities.OrderDto;
import com.evently.booking.order.entities.OrderRequest;
import dtos.Headers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> syncOrderItems(@RequestHeader(Headers.USER_ID) Long userId,
                                            @RequestBody OrderRequest orderRequest) {
        orderService.addTicketsToOrder(userId, orderRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<OrderDto> getOrderItems(@RequestHeader(Headers.USER_ID) Long userId) {
        var result = orderService.getActiveUserOrder(userId);
        return ResponseEntity.ok(orderService.getActiveUserOrder(userId));
    }

    @PutMapping("/expire")
    public ResponseEntity<?> expireActiveUserOrder(@RequestHeader("X-User-Id") Long userId) {
        orderService.expireActiveUserOrder(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestHeader(Headers.USER_ID) Long userId) {
        orderService.cancelActiveOrder(userId);

        return ResponseEntity.ok().build();
    }

}