package com.evently.booking.ticket;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class CheckAvailabilityRequest {
    public int tickets;
    public int eventsLocationsId;

    public CheckAvailabilityRequest(int tickets, int eventsLocationsId) {
        this.tickets = tickets;
        this.eventsLocationsId = eventsLocationsId;
    }
}