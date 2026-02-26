package com.evently.payment.payments.payment.service;

import com.evently.payment.payments.providers.stripe.model.StripePaymentRequest;
import com.evently.payment.payments.providers.stripe.model.PaymentStatus;
import com.evently.payment.payments.providers.contracts.PaymentProvider;
import com.evently.payment.payments.entities.PaymentProcessingResult;
import com.evently.payment.payments.payment.model.Payment;
import com.evently.payment.payments.payment.repository.PaymentRepository;
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

    public PaymentProcessingResult processPayment(
                                                  StripePaymentRequest paymentRequest) {
        PaymentProvider paymentProvider = paymentProviders.get(0);

        PaymentProcessingResult paymentProcessingResult =
                paymentProvider.process(
                        paymentRequest);

        Payment payment = new Payment();
        payment.setUserId(paymentRequest.getUserId());
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