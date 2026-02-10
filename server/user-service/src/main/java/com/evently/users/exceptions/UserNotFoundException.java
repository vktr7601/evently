package com.evently.users.exceptions;

public class InvalidUserEmailException extends RuntimeException{
    public InvalidUserEmailException(String email) {
        super(String.format("User with email [%s] not found", email));
    }
}