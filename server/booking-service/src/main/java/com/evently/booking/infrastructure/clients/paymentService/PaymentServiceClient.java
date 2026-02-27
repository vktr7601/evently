package com.evently.booking.infrastructure.clients.paymentService;

import com.evently.booking.infrastructure.clients.paymentService.data.PaymentRequest;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentServiceResponse;
import com.evently.booking.infrastructure.clients.paymentService.data.RefundRequest;
import com.evently.booking.infrastructure.clients.paymentService.data.RefundServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping("/payments/process")
    ResponseEntity<PaymentServiceResponse> processPayment(PaymentRequest paymentServiceRequest);

    @PostMapping("/payments/refund")
    ResponseEntity<RefundServiceResponse> processRefund(RefundRequest paymentServiceRequest);
}