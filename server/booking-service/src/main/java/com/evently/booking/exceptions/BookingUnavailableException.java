package com.evently.booking.exceptions;

public class BookingUnavailableException extends RuntimeException {
    public BookingUnavailableException(long eventId) {
        super("The requested tickets for this event are currently unavailable. Please try a different quantity or event.");
    }
}