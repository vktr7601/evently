package com.evently.events.eventsLocations.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
public class EventsLocationsDto {
    private long id;
    private String locationName;
    private LocalDateTime eventStartTime;
    private BigDecimal pricePerTicket;
    private EventsLocationsStataus status;

    public EventsLocationsDto(long id, String locationName, LocalDateTime eventStartTime, BigDecimal pricePerTicket, EventsLocationsStataus status) {
        setId(id);
        setLocationName(locationName);
        setEventStartTime(eventStartTime);
        setPricePerTicket(pricePerTicket);
        setStatus(status);
    }
}