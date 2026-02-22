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
    @JsonProperty("id")
    private Long id;
    @JsonProperty("locationId")
    private long locationId;
    @JsonProperty("eventStartTime")
    private LocalDateTime eventStartTime;
    @JsonProperty("ticketsCount")
    private int tickets;
    @JsonProperty("pricePerTicket")
    private BigDecimal price;
}