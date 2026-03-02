package com.evently.booking.infrastructure.exceptions;

import dto.payment.payment.PaymentResponse;

public class ProcessOrderException extends RuntimeException {
    public ProcessOrderException(PaymentResponse paymentResponse) {
        super("Payment processing failed: " + paymentResponse.getMessage());
    }
}