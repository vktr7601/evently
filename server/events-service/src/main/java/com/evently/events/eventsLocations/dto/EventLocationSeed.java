package com.evently.events.eventsLocations.dto;

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
public class EventLocationSeed implements Serializable {
    @JsonProperty("eventId")
    private long eventId;
    @JsonProperty("locationId")
    private long locationId;
    @JsonProperty("eventStartTime")
    private LocalDateTime eventStartTime;
    @JsonProperty("ticketsCount")
    private int ticketsCount;
    @JsonProperty("pricePerTicket")
    private BigDecimal pricePerTicket;
    @JsonProperty("status")
    private String status;
}