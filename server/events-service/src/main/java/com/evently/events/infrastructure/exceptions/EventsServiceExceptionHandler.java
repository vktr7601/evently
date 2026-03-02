package com.evently.events.infrastructure.exceptions;

import exceptions.DuplicateResourceException;
import exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import exceptions.EventlyErrorResponse;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class EventsServiceExceptionHandler {
    @ExceptionHandler(LocationCollisionException.class)
    public ResponseEntity<EventlyErrorResponse> handleLocationCollision(LocationCollisionException ex, HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.CONFLICT.value()).message(ex.getMessage()).errorCode("LOCATION_COLLISION").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<EventlyErrorResponse> handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.CONFLICT.value()).message(ex.getMessage()).errorCode("DUPLICATE_RESOURCE").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EventlyErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message =
                ex.getBindingResult().getAllErrors().stream().map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage()).collect(Collectors.joining(", "));

        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(message).errorCode("VALIDATION_FAILED").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(FormValidationException.class)
    public ResponseEntity<EventlyErrorResponse> handleFormValidation(FormValidationException ex, HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorCode("FORM_VALIDATION_FAILED").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<EventlyErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.NOT_FOUND.value()).message(ex.getMessage()).errorCode("RESOURCE_NOT_FOUND").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EventlyErrorResponse> handleGenericException(Exception ex,
                                                                       HttpServletRequest request) {
        EventlyErrorResponse error =
                EventlyErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("An unexpected error occurred").errorCode("INTERNAL_SERVER_ERROR").timestamp(LocalDateTime.now()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}