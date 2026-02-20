package com.evently.booking.refund;

import com.evently.booking.infrastructure.exceptions.OrderNotRefundableException;
import dtos.Headers;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refunds")
@AllArgsConstructor
public class RefundController {
    private final RefundService refundService;

    @GetMapping("/{number}/refundable")
    public ResponseEntity<Boolean> isEligibleForRefund(@RequestHeader(Headers.USER_ID) Long userId, @PathVariable Long number) {
        if (refundService.isOrderRefundable(userId, number)) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{number}/refund")
    public ResponseEntity<?> refundOrder(@RequestHeader(Headers.USER_ID) Long userId, @PathVariable Long number) throws OrderNotRefundableException {
        refundService.refundOrder(number, userId);
        return ResponseEntity.noContent().build();
    }

}