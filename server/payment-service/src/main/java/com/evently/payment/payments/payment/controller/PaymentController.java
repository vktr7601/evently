package com.evently.payment.payments.payment.controller;

import com.evently.payment.payments.entities.PaymentProcessingResult;
import com.evently.payment.payments.payment.service.PaymentService;
import com.evently.payment.payments.providers.stripe.model.StripePaymentRequest;
import constants.ApplicationHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentProcessingResult> processPayment( @RequestBody StripePaymentRequest paymentRequest) {
        PaymentProcessingResult paymentGatewayResponse =
                paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok().body(paymentGatewayResponse);
    }
}