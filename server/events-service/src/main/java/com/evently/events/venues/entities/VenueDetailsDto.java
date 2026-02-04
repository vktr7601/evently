package com.evently.events.venues.entities;

import com.evently.events.event.entities.EventDto;

import java.util.List;

public record VenueDetailsDto(String name, String imageUrl, List<EventDto> events) {
}