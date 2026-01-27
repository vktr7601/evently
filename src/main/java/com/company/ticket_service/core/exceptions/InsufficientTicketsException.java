package com.company.ticket_service.core.exceptions;

public class InsufficientTicketsException extends RuntimeException {

  public InsufficientTicketsException(String message) {
    super(message);
  }

  public static InsufficientTicketsException soldOut(String eventName) {
    return new InsufficientTicketsException("Event '%s' is sold out".formatted(eventName));
  }

  public static InsufficientTicketsException notEnough(int requested, int available) {
    return new InsufficientTicketsException(
        "Requested %d tickets but only %d available".formatted(requested, available));
  }
}