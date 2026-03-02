package com.evently.users.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import exceptions.EventlyErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class UserServiceExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<EventlyErrorResponse> handleDuplicateEmail(
            DuplicateEmailException ex, HttpServletRequest request) {

        EventlyErrorResponse error = EventlyErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .errorCode("DUPLICATE_EMAIL")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<EventlyErrorResponse> handleUserNotFound(
            UserNotFoundException ex, HttpServletRequest request) {

        EventlyErrorResponse error = EventlyErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .errorCode("USER_NOT_FOUND")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<EventlyErrorResponse> handleBadCredentials(
            BadCredentialsException ex, HttpServletRequest request) {

        EventlyErrorResponse error = EventlyErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .errorCode("BAD_CREDENTIALS")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}