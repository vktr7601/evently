package com.evently.payment.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public void processPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.processPayment(1l, paymentRequest);
    }

    @GetMapping("/history")
    public void getPaymentHistory(@RequestHeader("X-User-Id") Long userId) {
        paymentService.getPaymentHistory(userId);

    }
}