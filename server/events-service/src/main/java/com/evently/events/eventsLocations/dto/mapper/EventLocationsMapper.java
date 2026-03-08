package com.evently.events.eventsLocations.dto.mapper;

import com.evently.events.event.model.Event;
import com.evently.events.eventsLocations.dto.EventLocationSeed;
import com.evently.events.eventsLocations.dto.request.EventsLocationsData;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.model.EventsLocations;
import com.evently.events.eventsLocations.model.EventsLocationsStatus;
import com.evently.events.locations.model.Location;
import org.springframework.stereotype.Component;

@Component

public class EventLocationsMapper {

    public EventsLocations toEntity(Event event, EventsLocationsData data,
                                    Location location) {
        EventsLocations entity = new EventsLocations();
        entity.setEvent(event);
        entity.setLocation(location);
        entity.setEventStartTime(data.getEventStartTime());
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
        dto.setEventStartTime(entity.getEventStartTime());
        dto.setPricePerTicket(entity.getPrice());
        dto.setEventsLocationsStatus(entity.getEventsLocationsStatus());
        dto.setTicketsCount(entity.getTotalTickets());
        return dto;
    }

    public EventsLocations toEntity(Event event, Location location,
                                    EventLocationSeed seed) {
        EventsLocations entity = new EventsLocations();
        entity.setEvent(event);
        entity.setLocation(location);
        entity.setEventStartTime(seed.getEventStartTime());
        entity.setPrice(seed.getPricePerTicket());
        entity.setTotalTickets(seed.getTicketsCount());
        entity.setEventsLocationsStatus(EventsLocationsStatus.fromString(seed.getStatus()));
        return entity;
    }
}