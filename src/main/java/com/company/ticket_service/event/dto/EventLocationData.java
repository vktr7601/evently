package com.company.ticket_service.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventLocationData {
    @JsonProperty("location")
    private String location;
    @JsonProperty("date")
    private LocalDateTime date;
    @JsonProperty("available_tickets")
    private int availableTickets;
    @JsonProperty("price")
    private BigDecimal price;
}
