package com.evently.payment.payments.paymentTransactions.controller;

import com.evently.payment.payments.paymentTransactions.service.PaymentTransactionsService;
import com.evently.payment.payments.provider.model.PaymentRequest;
import com.evently.payment.payments.provider.model.PaymentResponse;
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