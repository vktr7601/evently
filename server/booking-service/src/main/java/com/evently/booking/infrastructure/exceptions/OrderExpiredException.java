package com.evently.booking.infrastructure.exceptions;

public class OrderExpiredException extends RuntimeException {
    public OrderExpiredException(String s) {
        super(s);
    }
}