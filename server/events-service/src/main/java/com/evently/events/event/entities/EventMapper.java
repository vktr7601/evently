package com.evently.events.event.entities;

import com.evently.events.event.Event;
import com.evently.events.eventsVenues.entities.EventsVenuesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eventsVenues", ignore = true)
    @Mapping(target = "performer", ignore = true)
   // @Mapping(target = "categories", ignore = true)
    Event toEntity(EventRequestDto dto);

    EventResponseDto toResponseDto(Event event, List<String> categories, List<EventsVenuesDto> eventsVenuesDtos);
}