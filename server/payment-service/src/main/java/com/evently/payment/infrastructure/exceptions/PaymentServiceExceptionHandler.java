package com.evently.payment.infrastructure.exceptions;

import exceptions.EventlyErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class PaymentServiceExceptionHandler {
    @ExceptionHandler(PaymentTransactionException.class)
    public ResponseEntity<EventlyErrorResponse> handleNoActiveOrderException(PaymentTransactionException ex, HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorCode("REFUNDED_TRANSACTION").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}