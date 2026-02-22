package com.evently.payment.payments;

import com.evently.payment.payments.gateway.PaymentGateway;
import com.evently.payment.payments.gateway.PaymentGatewayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;

    public PaymentGatewayResponse processPayment(Long userId,
                                                 PaymentRequest paymentRequest) {

        PaymentGatewayResponse gatewayResponse = paymentGateway.charge(
                paymentRequest.getAmount(),
                paymentRequest.getCardNumber(),
                paymentRequest.getCardExpiry(),
                paymentRequest.getCardCvv()
        );

        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setOrderId(paymentRequest.getOrderId());

        if (gatewayResponse.isSuccess()) {

            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setTransactionId(gatewayResponse.getTransactionId());
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }
        paymentRepository.save(payment);

        return gatewayResponse;
    }

    public void getPaymentHistory(long userId) {
        // Implement logic to retrieve payment history for a user
    }

    public RefundResponse processRefund(RefundRequest refundRequest) {
        try {
            // Simulate network latency (0.5 to 1.5 seconds)
            Thread.sleep(500 + (long) (Math.random() * 1000));

            // Simulate a 95% success rate
            if (Math.random() > 0.05) {
                return new RefundResponse(true, "REF-" + UUID.randomUUID(),
                        "Refund successful");
            } else {
                return new RefundResponse(false, null, "Bank rejected the " +
                        "refund: Insufficient merchant funds");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new RefundResponse(false, null, "Internal system error");
        }
    }
}