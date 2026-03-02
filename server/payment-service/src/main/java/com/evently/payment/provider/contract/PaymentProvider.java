package com.evently.payment.provider.contract;

import com.evently.payment.provider.dto.PaymentProviderResult;
import dto.payment.payment.PaymentRequest;
import dto.payment.refund.RefundRequest;
import dto.payment.refund.RefundResponse;

public interface PaymentProvider {
    PaymentProviderResult processPayment(PaymentRequest paymentRequest);

    RefundResponse processRefund(RefundRequest refundRequest);
}