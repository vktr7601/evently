package com.evently.events.infra.exceptions;

import java.util.List;

public class LocationCollisionException extends RuntimeException {
    public LocationCollisionException(List<String> collisions) {
        super("Location collision detected for the following locations: " + String.join(", ", collisions));
    }
}