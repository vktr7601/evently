package com.evently.events.event.entities;

import com.evently.events.artists.Artist;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.event.Event;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import events.eventCreated.EventCreated;
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
    Event toEntity(CreateEventRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eventLocations", ignore = true)
    @Mapping(target = "imageUrl", source = "artist.imageUrl")
    @Mapping(target = "artist", source = "artist")
    Event toEntity(CreateEventRequest dto, Artist artist);

    @Mapping(source = "event.id", target = "id")
    @Mapping(source = "event.name", target = "eventName")
    @Mapping(source = "event.description", target = "description")
    @Mapping(source = "event.imageUrl", target = "imageUrl")
    @Mapping(source = "event.artist", target = "artist")
    @Mapping(source = "locations", target = "eventsLocations")
    @Mapping(source = "categories", target = "categoryDtoList")
    EventDetailDto toDetailDto(Event event, List<EventsLocationsDto> locations, List<CategoryDto> categories);
}