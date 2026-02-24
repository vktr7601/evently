package com.evently.booking.infrastructure.clients.paymentService;

import com.evently.booking.infrastructure.clients.paymentService.data.PaymentServiceResponse;
import com.evently.booking.order.entities.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service", url = "http://localhost:8084")
public interface PaymentServiceClient {

    @PostMapping("/payments/process")
    ResponseEntity<PaymentServiceResponse> processPayment(PaymentRequest paymentServiceRequest);
}