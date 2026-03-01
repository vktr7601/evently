package com.evently.payment.payments.paymentTransactions.controller;

import com.evently.payment.payments.paymentTransactions.service.PaymentTransactionsService;
import dto.payment.payment.PaymentRequest;
import dto.payment.payment.PaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/payment-transactions")
public class PaymentTransactionsController {
    private final PaymentTransactionsService paymentTransactionsService;

    @PostMapping
    public ResponseEntity<PaymentResponse> handlePayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse =
                paymentTransactionsService.charge(paymentRequest);

        return ResponseEntity.ok(paymentResponse);
    }
}