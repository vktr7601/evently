package com.evently.events.event.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventLocationData {
    @JsonProperty("location")
    private long locationId;
    @JsonProperty("date")
    private LocalDateTime date;
    @JsonProperty("tickets")
    private int tickets;
    @JsonProperty("price")
    private BigDecimal price;
}