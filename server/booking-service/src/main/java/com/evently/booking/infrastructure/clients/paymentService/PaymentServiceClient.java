package com.evently.booking.infrastructure.clients.paymentService;

import com.evently.booking.infrastructure.clients.paymentService.data.PaymentRequest;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @PostMapping("/payments/process")
    ResponseEntity<PaymentServiceResponse> processPayment(PaymentRequest paymentServiceRequest);
}