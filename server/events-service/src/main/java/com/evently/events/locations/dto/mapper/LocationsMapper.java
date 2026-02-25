package com.evently.events.locations.dto.mapper;

import com.evently.events.eventsLocations.model.EventsLocations;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import org.springframework.stereotype.Component;

@Component
public class LocationsMapper {
    public EventsLocationsDto toDto(EventsLocations entity) {
        if (entity == null) {
            return null;
        }

        EventsLocationsDto dto = new EventsLocationsDto();

        dto.setId(entity.getId());
        dto.setEventStartTime(entity.getDate());
        dto.setPricePerTicket(entity.getPrice());
        dto.setTicketsCount(entity.getTotalTickets());
        dto.setEventsLocationsStatus(entity.getEventsLocationsStatus());

        if (entity.getEvent() != null) {
            dto.setEventId(entity.getEvent().getId());
            dto.setEventName(entity.getEvent().getName());
        }

        if (entity.getLocation() != null) {
            dto.setLocationId(entity.getLocation().getId());
            dto.setLocationName(entity.getLocation().getName());
        }

        return dto;
    }
}