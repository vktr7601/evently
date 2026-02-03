package com.evently.events.event.entities;

import com.evently.events.eventLocations.entities.EventLocationsDto;

import java.util.List;

public record EventResponseDto(
        long id,
        String name,
        String description,
        List<String> categories,
        List<EventLocationsDto> eventLocationsDtos
){}