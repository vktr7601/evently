package com.evently.events.eventsVenues.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventsVenuesDto(String eventName, String venue, LocalDateTime date, BigDecimal price,
                              int totalTickets) {
}