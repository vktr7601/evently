package com.evently.payment.payments;

import com.evently.payment.payments.gateway.PaymentGatewayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentGatewayResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentGatewayResponse paymentGatewayResponse = paymentService.processPayment(1l, paymentRequest);
        return ResponseEntity.ok().body(paymentGatewayResponse);
    }

    @GetMapping("/history")
    public void getPaymentHistory(@RequestHeader("X-User-Id") Long userId) {
        paymentService.getPaymentHistory(userId);
    }

    @PostMapping("/refund")
    public ResponseEntity<RefundResponse> refundPayment(@RequestBody RefundRequest refundRequest) {
        RefundResponse paymentGatewayResponse = paymentService.processRefund(refundRequest);
        return ResponseEntity.ok().body(paymentGatewayResponse);
    }
}