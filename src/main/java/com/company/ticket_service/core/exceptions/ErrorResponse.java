package com.company.ticket_service.core.exceptions;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ErrorResponse {
  private String message;
  private LocalDateTime localDateTime;

  public ErrorResponse(String message, LocalDateTime localDateTime) {
    this.message = message;
    this.localDateTime = localDateTime;
  }
}