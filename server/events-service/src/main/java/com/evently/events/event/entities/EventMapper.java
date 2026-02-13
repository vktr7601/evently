package com.evently.events.event.entities;

import com.evently.events.category.entities.CategoryDto;
import com.evently.events.event.Event;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eventLocations", ignore = true)
    @Mapping(target = "artist", ignore = true)
    Event toEntity(EventRequestDto dto);

    @Mapping(source = "event.id", target = "id")
    @Mapping(source = "event.name", target = "name")
    @Mapping(source = "event.description", target = "description")
    @Mapping(source = "event.imageUrl", target = "imageUrl")
    @Mapping(source = "event.artist", target = "artist")
    @Mapping(source = "locations", target = "eventLocationData")
    @Mapping(source = "categories", target = "categoryDtoList")
    EventDetailDto toDetailDto(Event event, List<EventsLocationsDto> locations, List<CategoryDto> categories);
}