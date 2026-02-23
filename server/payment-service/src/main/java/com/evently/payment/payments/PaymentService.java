package com.evently.payment.payments;

import com.evently.payment.payments.contracts.PaymentProvider;
import com.evently.payment.payments.entities.PaymentProcessingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final List<PaymentProvider> paymentProviders;

    public PaymentProcessingResult processPayment(Long userId,
                                                  PaymentRequest paymentRequest) {
        PaymentProvider paymentProvider = paymentProviders.get(0);

        PaymentProcessingResult paymentProcessingResult =
                paymentProvider.process(userId,
                        paymentRequest);

        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setOrderId(payment.getOrderId());
        payment.setAmount(payment.getAmount());
        payment.setTransactionId(paymentProcessingResult.getTransactionId());
        payment.setStatus(paymentProcessingResult.isSuccess() ?
                PaymentStatus.COMPLETED :
                PaymentStatus.FAILED);
        payment.setReceiptUrl(paymentProcessingResult.getReceiptUrl());

        paymentRepository.save(payment);

        return paymentProcessingResult;
    }
}