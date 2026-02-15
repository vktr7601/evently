package com.evently.booking.order;

import com.evently.booking.order.entities.OrderDto;
import com.evently.booking.order.entities.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);

        return ResponseEntity.ok(order.getId());
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderDetails(1l, id));
    }

}