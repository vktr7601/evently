package com.evently.payment.payments.providers.contracts;

import com.evently.payment.payments.providers.stripe.model.StripePaymentRefund;
import com.evently.payment.payments.providers.stripe.model.StripePaymentRequest;
import com.evently.payment.payments.entities.PaymentProcessingResult;

public interface PaymentProvider {
    PaymentProcessingResult process(StripePaymentRequest request);

    PaymentProcessingResponse processRefund(StripePaymentRefund  refund);
}