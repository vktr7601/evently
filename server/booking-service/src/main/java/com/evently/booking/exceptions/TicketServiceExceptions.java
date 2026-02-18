package com.evently.booking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class TicketServiceExceptions {
    @ExceptionHandler(NoActiveOrderException.class)
    public ResponseEntity<Object> handleNoActiveOrderException(NoActiveOrderException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NO_CONTENT.value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProcessOrderException.class)
    public ResponseEntity<Object> handleProcessOrderException(ProcessOrderException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("messae", HttpStatus.NO_CONTENT.value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketNotRefundableException.class)
    public ResponseEntity<Object> handleTicketNotRefundableException(TicketNotRefundableException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingUnavailableException.class)
    public ResponseEntity<String> handleBookingError(BookingUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.GONE).body(ex.getMessage());
    }
}