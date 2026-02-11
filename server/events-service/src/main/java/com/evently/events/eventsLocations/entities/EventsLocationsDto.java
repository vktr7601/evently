package com.evently.events.eventsLocations.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
public class EventsLocationsDto {
    private long eventLocationId;
    private long eventId;
    private long locationId;
    private String eventName;
    private String locationName;
    private LocalDateTime eventStartTime;
    private BigDecimal pricePerTicket;
    private EventsLocationsStataus status;

    public EventsLocationsDto(long eventLocationId, long eventId, long locationId, String eventName, String locationName, LocalDateTime eventStartTime, BigDecimal pricePerTicket, EventsLocationsStataus status) {
        setEventLocationId(eventLocationId);
        setEventId(eventId);
        setLocationId(locationId);
        setEventName(eventName);
        setLocationName(locationName);
        setEventStartTime(eventStartTime);
        setPricePerTicket(pricePerTicket);
        setStatus(status);
    }
}