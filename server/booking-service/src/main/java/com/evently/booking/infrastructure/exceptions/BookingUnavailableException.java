package com.evently.booking.infrastructure.exceptions;

public class BookingUnavailableException extends RuntimeException {
    public BookingUnavailableException() {
        super("The requested tickets for this event are currently unavailable. Please try a different quantity or event.");
    }
}