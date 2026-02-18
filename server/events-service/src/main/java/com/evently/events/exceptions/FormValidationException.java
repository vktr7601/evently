package com.evently.events.exceptions;

public class FormValidationException extends RuntimeException {
    public FormValidationException(String message) {
        super(message);
    }
}