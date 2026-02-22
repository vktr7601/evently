package com.evently.events.config;

import com.evently.events.event.Event;
import com.evently.events.eventsLocations.EventsLocations;
import com.evently.events.eventsLocations.entities.EventsLocationsData;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.entities.EventsLocationsStatus;
import com.evently.events.locations.Location;
import org.springframework.stereotype.Component;

@Component

public class EventLocationsMapper {

    public EventsLocations toEntity(Event event, EventsLocationsData data,
                                    Location location) {
        EventsLocations entity = new EventsLocations();
        entity.setEvent(event);
        entity.setLocation(location);
        entity.setDate(data.getEventStartTime());
        entity.setTotalTickets(data.getTickets());
        entity.setPrice(data.getPrice());
        entity.setEventsLocationsStatus(EventsLocationsStatus.PENDING_TICKETS);
        return entity;
    }

    public EventsLocationsDto toDto(EventsLocations entity) {
        EventsLocationsDto dto = new EventsLocationsDto();
        dto.setId(entity.getId());
        dto.setEventId(entity.getEvent().getId());
        dto.setLocationId(entity.getLocation().getId());
        dto.setEventName(entity.getEvent().getName());
        dto.setLocationName(entity.getLocation().getName());
        dto.setEventStartTime(entity.getDate());
        dto.setPricePerTicket(entity.getPrice());
        dto.setEventsLocationsStatus(entity.getEventsLocationsStatus());
        dto.setTicketsCount(entity.getTotalTickets());
        return dto;
    }
}