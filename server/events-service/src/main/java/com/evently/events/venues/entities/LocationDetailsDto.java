package com.evently.events.venues.entities;

import com.evently.events.event.entities.EventDetailsDto;

import java.util.List;

public record LocationDetailsDto(String name, String imageUrl, List<EventDetailsDto> events) {
}