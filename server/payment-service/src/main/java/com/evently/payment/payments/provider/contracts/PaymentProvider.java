package com.evently.payment.payments.provider.contracts;

import com.evently.payment.payments.provider.model.PaymentProviderResult;
import dto.payment.payment.PaymentRequest;
import dto.payment.payment.PaymentResponse;
import dto.payment.refund.RefundRequest;
import dto.payment.refund.RefundResponse;

public interface PaymentProvider {
    PaymentProviderResult processPayment(PaymentRequest paymentRequest);

    RefundResponse processRefund(RefundRequest refundRequest);
}