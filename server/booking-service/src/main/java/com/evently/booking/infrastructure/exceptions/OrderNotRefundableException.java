package com.evently.booking.infrastructure.exceptions;

public class OrderNotRefundableException extends Throwable {
    public OrderNotRefundableException(long number) {
        super(String.format("Order with number %d is not refundable", number));
    }
}