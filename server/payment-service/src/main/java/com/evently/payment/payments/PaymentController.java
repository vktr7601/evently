package com.evently.payment.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        String transactionId = paymentService.processPayment(1l, paymentRequest);
        return ResponseEntity.ok().body(transactionId);
    }

    @GetMapping("/history")
    public void getPaymentHistory(@RequestHeader("X-User-Id") Long userId) {
        paymentService.getPaymentHistory(userId);

    }
}