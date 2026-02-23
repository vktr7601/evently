package com.evently.payment.payments.contracts;

import com.evently.payment.payments.PaymentRequest;
import com.evently.payment.payments.entities.PaymentProcessingResult;

public interface PaymentProvider {
    PaymentProcessingResult process(Long userId, PaymentRequest request);
}