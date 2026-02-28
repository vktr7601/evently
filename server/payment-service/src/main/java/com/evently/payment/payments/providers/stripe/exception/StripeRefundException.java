package com.evently.payment.payments.providers.stripe.exception;

public class StripeRefundException extends RuntimeException {
    public StripeRefundException(String s) {
        super(s);
    }
}