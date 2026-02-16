package com.evently.booking.exceptions;

public class NoActiveOrderException extends RuntimeException {
    public NoActiveOrderException(long userId) {
        super("No active order found for user with id: " + userId);
    }
}