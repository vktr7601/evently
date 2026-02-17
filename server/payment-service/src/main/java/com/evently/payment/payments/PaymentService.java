package com.evently.payment.payments;

import com.evently.payment.payments.gateway.PaymentGateway;
import com.evently.payment.payments.gateway.PaymentGatewayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;

    public String processPayment(Long userId, PaymentRequest paymentRequest) {

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

        return  payment.getTransactionId();
    }

    public void getPaymentHistory(long userId) {
        // Implement logic to retrieve payment history for a user
    }

}