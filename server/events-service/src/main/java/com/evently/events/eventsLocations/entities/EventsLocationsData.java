package com.evently.events.eventsLocations.entities;

import com.evently.events.event.entities.ValidLocationData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ValidLocationData
@NoArgsConstructor
public class EventsLocationsData {
    @JsonProperty("eventLocationId")
    private Long eventLocationId;
    @JsonProperty("locationId")
    private long locationId;
    @JsonProperty("eventDate")
    private LocalDateTime eventDate;
    @JsonProperty("tickets")
    private int tickets;
    @JsonProperty("price")
    private BigDecimal price;
}