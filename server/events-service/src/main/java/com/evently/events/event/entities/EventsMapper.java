package com.evently.events.event.entities;

import com.evently.events.artists.Artist;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.event.Event;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventsMapper {


    public Event toEntity(EventCreate dto) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());

        return event;
    }

    public Event toEntity(EventCreate dto, Artist artist) {
        if (dto == null && artist == null) {
            return null;
        }

        Event event = new Event();

        if (dto != null) {
            event.setName(dto.getName());
            event.setDescription(dto.getDescription());
        }

        if (artist != null) {
            event.setArtist(artist);
            event.setImageUrl(artist.getImageUrl());
        }

        return event;
    }

    public EventDetailDto toDetailDto(Event event,
                                      List<EventsLocationsDto> locations,
                                      List<CategoryDto> categories) {
        if (event == null) {
            return null;
        }

        EventDetailDto dto = new EventDetailDto(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getImageUrl(),
                event.getArtist()
        );

        dto.setEventsLocations(locations);
        dto.setCategoryDtoList(categories);

        return dto;
    }
}