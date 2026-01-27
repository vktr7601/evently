package com.company.ticket_service.core.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateException(
      DuplicateResourceException duplicateResourceException) {
    ErrorResponse errorResponse =
        new ErrorResponse(duplicateResourceException.getMessage(), LocalDateTime.now());

    return ResponseEntity.status(409).body(errorResponse);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException resourceNotFoundException) {
    ErrorResponse errorResponse =
        new ErrorResponse(resourceNotFoundException.getMessage(), LocalDateTime.now());

    return ResponseEntity.status(404).body(errorResponse);
  }

  @ExceptionHandler(InsufficientTicketsException.class)
  public ResponseEntity<ErrorResponse> handleInsufficientTickets(InsufficientTicketsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ErrorResponse(ex.getMessage(), LocalDateTime.now()));
  }
}