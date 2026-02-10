package com.evently.users.exceptions;

public class DublicateEmailException extends RuntimeException {
    public DublicateEmailException(String email) {
        super(String.format("User with email [%s] already exists", email));
    }
}