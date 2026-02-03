package com.evently.events.eventLocations.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventLocationsDto(String eventName, String location, LocalDateTime date, BigDecimal price,
                                int totalTickets) {
}