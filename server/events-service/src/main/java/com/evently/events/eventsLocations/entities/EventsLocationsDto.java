package com.evently.events.eventsLocations.entities;

import com.evently.events.eventsLocations.model.EventsLocationsStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventsLocationsDto implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("eventId")
    private long eventId;
    @JsonProperty("locationId")
    private long locationId;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("locationName")
    private String locationName;
    @JsonProperty("eventStartTime")
    private LocalDateTime eventStartTime;
    @JsonProperty("eventsLocationsStatus")
    private EventsLocationsStatus eventsLocationsStatus;
    @JsonProperty("pricePerTicket")
    private BigDecimal pricePerTicket;
    @JsonProperty("ticketsCount")
    private long ticketsCount;
}