package com.evently.events.event.entities;

import com.evently.events.event.Event;
import com.evently.events.eventLocations.entities.EventLocationsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eventsLocations", ignore = true)
    @Mapping(target = "performer", ignore = true)
    @Mapping(target = "eventsCategories", ignore = true)
    Event toEntity(EventRequestDto dto);

    EventResponseDto toResponseDto(Event event, List<String> categories, List<EventLocationsDto> eventLocationsDtos);
}