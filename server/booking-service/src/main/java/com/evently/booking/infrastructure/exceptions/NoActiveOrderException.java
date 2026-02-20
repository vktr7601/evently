package com.evently.booking.infrastructure.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoActiveOrderException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(NoActiveOrderException.class);

    public NoActiveOrderException(long userId) {
        super("You don't have an active booking session at the moment.");
        log.info("User with ID {} attempted to perform an action that requires an active booking session, but no active session was found.", userId);
    }
}