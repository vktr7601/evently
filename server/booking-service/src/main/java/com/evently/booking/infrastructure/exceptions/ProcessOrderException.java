package com.evently.booking.infrastructure.exceptions;

import com.evently.booking.infrastructure.clients.paymentService.data.PaymentServiceResponse;

public class ProcessOrderException extends RuntimeException {
    public ProcessOrderException(PaymentServiceResponse paymentServiceResponse) {
        super("Payment processing failed: " + paymentServiceResponse.getMessage());
    }
}