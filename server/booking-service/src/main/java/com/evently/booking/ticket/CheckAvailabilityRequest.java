package com.evently.booking.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class CheckAvailabilityRequest {
    @JsonProperty("tickets")
    public int tickets;
    @JsonProperty("eventsLocationsId")
    public int eventsLocationsId;
    // public String promoCode;

    public CheckAvailabilityRequest(int tickets, int eventsLocationsId) {
        setTickets(tickets);
        setEventsLocationsId(eventsLocationsId);
    }
}