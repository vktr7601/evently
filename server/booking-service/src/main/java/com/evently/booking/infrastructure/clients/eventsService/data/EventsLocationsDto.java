package com.evently.booking.infrastructure.clients.eventsService.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter(PROTECTED)
@NoArgsConstructor
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
    private Object eventsLocationsStatus;
    @JsonProperty("pricePerTicket")
    private BigDecimal pricePerTicket;
    @JsonProperty("ticketsCount")
    private long ticketsCount;
}