package com.evently.booking.order;

import com.evently.booking.order.entities.OrderDto;
import com.evently.booking.order.entities.OrderRequest;
import dtos.Headers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestHeader(Headers.USER_ID) Long userId,
                                                    @RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(userId, orderRequest);
        Map<String, Long> map = Map.of("id", order.getId());
        return ResponseEntity.ok(map);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderDetails(1l, id));
    }

    @GetMapping("/active")
    public ResponseEntity<OrderDto> getUserOrder(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(orderService.getActiveUserOrder(userId));
    }

//    @PutMapping("/expire")
//    public ResponseEntity<?> expireActiveUserOrder(@RequestHeader("X-User-Id") Long userId) {
//
//    }

}