package com.evently.events.eventsLocations.model;

import lombok.Getter;

@Getter
public enum EventsLocationsStatus {
    CANCELLED("CANCELLED"), AVAILABLE("AVAILABLE"), COMPLETED("COMPLETED"),
    PENDING_TICKETS("PENDING_TICKETS"),SOLD_OUT("SOLD_OUT");
    private final String name;

    EventsLocationsStatus(String name) {
        this.name = name;
    }

    public static EventsLocationsStatus fromString(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status string cannot be null");
        }

        for (EventsLocationsStatus value : EventsLocationsStatus.values()) {
            if (value.name.equalsIgnoreCase(status.trim())) {
                return value;
            }
        }

        throw new IllegalArgumentException("No enum constant found for " +
                "status: " + status);
    }

}