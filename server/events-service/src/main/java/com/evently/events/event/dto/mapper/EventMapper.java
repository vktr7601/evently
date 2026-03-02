package com.evently.events.event.dto.mapper;

import com.evently.events.artists.model.Artist;
import com.evently.events.category.dto.CategoryDto;
import com.evently.events.event.dto.request.EventCreateRequest;
import com.evently.events.event.dto.EventDetailDto;
import com.evently.events.event.model.Event;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import events.ticket.TicketsCreationEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventMapper {
    public Event toEntity(EventCreateRequest dto) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());

        return event;
    }

    public Event toEntity(EventCreateRequest dto, Artist artist) {
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

    public TicketsCreationEvent toTicketsCreationEvent(EventsLocationsDto location) {
        return new TicketsCreationEvent(
                location.getId(),
                location.getTicketsCount(),
                location.getEventStartTime(),
                location.getPricePerTicket()
        );
    }
}