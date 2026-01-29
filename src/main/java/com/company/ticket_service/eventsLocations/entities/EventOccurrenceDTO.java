package com.company.ticket_service.eventsLocations.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventOccurrenceDTO(
        String locationName,
        String eventName,
        LocalDateTime date,
        String description,
        BigDecimal price
) {
}
