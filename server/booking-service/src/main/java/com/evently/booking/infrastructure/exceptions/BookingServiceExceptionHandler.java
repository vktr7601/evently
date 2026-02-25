package com.evently.booking.infrastructure.exceptions;

import exceptions.EventlyErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BookingServiceExceptionHandler {
    @ExceptionHandler(NoActiveOrderException.class)
    public ResponseEntity<EventlyErrorResponse> handleNoActiveOrderException(NoActiveOrderException ex, HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorCode("NO_ACTIVE_ORDER").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ProcessOrderException.class)
    public ResponseEntity<EventlyErrorResponse> handleProcessOrderException(ProcessOrderException ex, HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorCode("PROCESS_ORDER_FAILED").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TicketNotRefundableException.class)
    public ResponseEntity<EventlyErrorResponse> handleTicketNotRefundableException(TicketNotRefundableException ex, HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorCode("TICKET_NOT_REFUNDABLE").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BookingUnavailableException.class)
    public ResponseEntity<EventlyErrorResponse> handleBookingUnavailable(BookingUnavailableException ex, HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.CONFLICT.value()).message(ex.getMessage()).errorCode("BOOKING_UNAVAILABLE").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EventlyErrorResponse> handleGenericException(Exception ex,
                                                                       HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("An unexpected error occurred").errorCode("INTERNAL_SERVER_ERROR").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(OrderExpiredException.class)
    public ResponseEntity<EventlyErrorResponse> handleException(OrderExpiredException ex, HttpServletRequest request) {
        EventlyErrorResponse error = new EventlyErrorResponse(
                HttpStatus.GONE.value(),
                "Order Expired",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.GONE);
    }

    @ExceptionHandler(PriceChangedException.class)
    public ResponseEntity<EventlyErrorResponse> handlePriceChanged(PriceChangedException ex, HttpServletRequest request) {
        EventlyErrorResponse error = EventlyErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .errorCode("PRICE_CHANGED")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}