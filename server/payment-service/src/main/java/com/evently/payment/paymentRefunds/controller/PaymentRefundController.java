package com.evently.payment.paymentRefunds.controller;

import com.evently.payment.paymentRefunds.service.PaymentRefundsService;
import dto.payment.refund.RefundRequest;
import dto.payment.refund.RefundResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/payment-refunds")
public class PaymentRefundController {
    private final PaymentRefundsService paymentRefundsService;

    @PostMapping
    public ResponseEntity<RefundResponse> handleRefund(@RequestBody RefundRequest refundRequest) {
        RefundResponse response =
                paymentRefundsService.handleRefund(refundRequest);

        return ResponseEntity.ok(response);
    }
}