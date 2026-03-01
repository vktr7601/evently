package com.evently.booking.infrastructure.clients.paymentService;

import com.evently.booking.infrastructure.clients.paymentService.data.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping("/payment-transactions")
    ResponseEntity<PaymentResponse> handlePayment(PaymentRequest paymentServiceRequest);

    @PostMapping("/payments/refund")
    ResponseEntity<RefundResponse> processRefund(RefundRequest paymentServiceRequest);
}