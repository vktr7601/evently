package com.evently.booking.exceptions;

import com.evently.booking.order.entities.PaymentServiceResponse;

public class ProcessOrderException extends RuntimeException {
    public ProcessOrderException(PaymentServiceResponse paymentServiceResponse) {
        super("Payment processing failed: " + paymentServiceResponse.getMessage());
    }
}