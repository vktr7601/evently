package com.evently.booking.infrastructure.exceptions;

public class PriceChangedException extends RuntimeException {
    public PriceChangedException(String message) {
        super(message);
    }
}