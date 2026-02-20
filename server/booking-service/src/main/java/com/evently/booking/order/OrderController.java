package com.evently.booking.order;

import com.evently.booking.order.entities.FinishOrderRequest;
import com.evently.booking.order.entities.OrderDetails;
import com.evently.booking.order.entities.OrderListItemDto;
import com.evently.booking.order.entities.OrderRequest;
import dtos.Headers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<OrderDetails> getActive(@RequestHeader(Headers.USER_ID) Long userId) {
        var result = orderService.getActiveUserOrder(userId);
        return ResponseEntity.ok(orderService.getActiveUserOrder(userId));
    }

//    @PutMapping("/expire")
//    public ResponseEntity<?> expireActiveUserOrder(@RequestHeader("X-User-Id") Long userId) {
//        orderService.expireActiveUserOrder(userId);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestHeader(Headers.USER_ID) Long userId) {
        orderService.cancelActiveOrder(userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderListItemDto>> getUserOrders(@RequestHeader(Headers.USER_ID) Long userId) {
        var orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/details/{number}")
    public ResponseEntity<OrderDetails> getOrderDetails(@RequestHeader(Headers.USER_ID) Long userId, @PathVariable Long number) {
        var orderDetails = orderService.getOrderDetails(userId, number);
        return ResponseEntity.ok(orderDetails);
    }


    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestHeader(Headers.USER_ID) Long userId, @RequestBody FinishOrderRequest finishOrderRequest) {
        orderService.finishActiveUserOrder(userId, finishOrderRequest);
        return ResponseEntity.noContent().build();
    }
}