package com.evently.booking.order;

import com.evently.booking.order.entities.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service", url = "http://localhost:8084")
public interface PaymentClient {

    @PostMapping("/payments/process")
    ResponseEntity<String> processPayment(PaymentRequest paymentRequest);
}