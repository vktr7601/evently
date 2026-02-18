package com.evently.booking.exceptions;

public class OrderNotRefundableException extends Throwable {
    public OrderNotRefundableException(long number) {
        super(String.format("Order with number %d is not refundable", number));
    }
}