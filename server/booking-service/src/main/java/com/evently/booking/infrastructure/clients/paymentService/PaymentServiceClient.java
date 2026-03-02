package com.evently.booking.infrastructure.clients.paymentService;

import dto.payment.payment.PaymentRequest;
import dto.payment.payment.PaymentResponse;
import dto.payment.refund.RefundRequest;
import dto.payment.refund.RefundResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping("/payment-transactions")
    ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentServiceRequest);

    @PostMapping("/payment-refunds")
    ResponseEntity<RefundResponse> processRefund(@RequestBody RefundRequest paymentServiceRequest);
}