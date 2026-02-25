package com.evently.booking.refund.controller;

import com.evently.booking.infrastructure.exceptions.OrderNotRefundableException;
import com.evently.booking.refund.service.RefundService;
import constants.ApplicationHeaders;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/refunds")
public class RefundController {
    private final RefundService refundService;

    @GetMapping("/{number}/refundable")
    public ResponseEntity<Boolean> isEligibleForRefund(@RequestHeader(ApplicationHeaders.USER_ID) Long userId, @PathVariable UUID number) {
        if (refundService.isOrderRefundable(userId, number)) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{number}/refund")
    public ResponseEntity<?> refundOrder(@RequestHeader(ApplicationHeaders.USER_ID) Long userId, @PathVariable Long number) throws OrderNotRefundableException {
        //  refundService.refundOrder(number, userId);
        return ResponseEntity.noContent().build();
    }

}