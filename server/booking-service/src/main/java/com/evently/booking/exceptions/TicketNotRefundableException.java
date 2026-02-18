package com.evently.booking.exceptions;

public class TicketNotRefundableException extends RuntimeException {
    public TicketNotRefundableException() {
        super("Ticket is not refundable. Refunds are only allowed for tickets that have been purchased and are within the refund period.");
    }
}