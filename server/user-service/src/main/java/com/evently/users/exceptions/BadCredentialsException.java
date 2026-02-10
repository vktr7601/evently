package com.evently.users.exceptions;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException() {
        super("Wrong credentials. Please check your email and password and try again.");
    }
}