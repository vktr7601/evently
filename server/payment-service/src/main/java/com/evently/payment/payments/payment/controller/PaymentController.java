package com.evently.payment.payments.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
//    private final PaymentService paymentService;
//
//    @PostMapping("/process")
//    public ResponseEntity<PaymentProcessingResult> processPayment
//    (@RequestBody StripePaymentRequest paymentRequest) {
//        PaymentProcessingResult paymentGatewayResponse =
//                paymentService.processPayment(paymentRequest);
//        return ResponseEntity.ok().body(paymentGatewayResponse);
//    }
//
//    @PostMapping("/refund")
//    public ResponseEntity<?> processRefund(@RequestBody StripePaymentRefund
//    paymentRefund) {
//        var paymentGatewayResponse =
//                paymentService.processRefund(paymentRefund);
//        return ResponseEntity.ok().body(paymentGatewayResponse);
//    }
//
//
//    @PostMapping("process-payment")
//    public ResponseEntity<PaymentResponse> processPayment(@RequestBody
//    PaymentRequest paymentRequest) {
//        return null;
//    }
//
//    @PostMapping("/process-refund")
//    public ResponseEntity<RefundResponse> processRefund(@RequestBody
//    RefundRequest refundRequest) {
//        return null;
//    }
}