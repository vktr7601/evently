package com.evently.users.exceptions;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
        super("Wrong credentials. Please check your email and password and try again.");
    }
}