package com.evently.payment.payments.provider.contracts;

import com.evently.payment.payments.provider.model.PaymentRequest;
import com.evently.payment.payments.provider.model.PaymentResponse;
import com.evently.payment.payments.provider.model.RefundRequest;
import com.evently.payment.payments.provider.model.RefundResponse;

public interface PaymentProvider {
    PaymentResponse processPayment(PaymentRequest paymentRequest);

    RefundResponse processRefund(RefundRequest refundRequest);
}