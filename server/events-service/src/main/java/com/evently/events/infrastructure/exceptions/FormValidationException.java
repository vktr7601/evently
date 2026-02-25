package com.evently.events.infrastructure.exceptions;

public class FormValidationException extends RuntimeException {
    public FormValidationException(String message) {
        super(message);
    }
}