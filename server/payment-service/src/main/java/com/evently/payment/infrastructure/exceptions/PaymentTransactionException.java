package com.evently.payment.infrastructure.exceptions;

public class PaymentTransactionException extends RuntimeException {
    public PaymentTransactionException(String s) {
        super(s);
    }
}