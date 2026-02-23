package com.evently.payment.payments;

import com.evently.payment.payments.entities.PaymentProcessingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentProcessingResult> processPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentProcessingResult paymentGatewayResponse = paymentService.processPayment(1l, paymentRequest);
        return ResponseEntity.ok().body(paymentGatewayResponse);
    }
}