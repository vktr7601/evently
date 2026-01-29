package com.company.ticket_service.event.dto;

import com.company.ticket_service.eventsLocations.EventsLocations;

import java.time.LocalDateTime;
import java.util.List;

public record EventDto(long id, String name, LocalDateTime eventDate, int tickets, List<String> classifications,
                       List<EventsLocations> locations) {
}