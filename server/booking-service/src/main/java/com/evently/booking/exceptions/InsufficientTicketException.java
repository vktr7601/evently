package com.evently.booking.exceptions;

public class InsufficientTicketException extends RuntimeException {
    public InsufficientTicketException(long eventId, int requestedTickets) {
        super(String.format("Event with id [%d], but [%d] were requested", eventId, requestedTickets));
    }
}