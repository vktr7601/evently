package com.evently.payment.payments.paymentTransactions.controller;

import com.evently.payment.payments.paymentTransactions.dto.PaymentTransactionDetails;
import com.evently.payment.payments.paymentTransactions.service.PaymentTransactionsService;
import constants.ApplicationHeaders;
import dto.payment.payment.PaymentRequest;
import dto.payment.payment.PaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    public ResponseEntity<PaymentTransactionDetails> getPaymentTransactionDetails(@PathVariable("id") Long id,
                                                                                  @RequestHeader(ApplicationHeaders.USER_ID) long userId) {
        PaymentTransactionDetails paymentTransactionDetails =
                paymentTransactionsService.getPaymentTransactionDetails(id, userId);

        return ResponseEntity.ok(paymentTransactionDetails);
    }
}