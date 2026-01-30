package com.company.ticket_service.event.entities;

import com.company.ticket_service.event.Event;
import com.company.ticket_service.eventsLocations.EventsLocations;
import com.company.ticket_service.eventsLocations.entities.EventLocationsDto;
import com.company.ticket_service.location.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.company.ticket_service.eventsLocations.entities.EventLocationStatus.AVAILABLE;

@Mapper(componentModel = "spring")
public interface EventsLocationMapper {

    EventsLocations toEntity(EventsLocations eventsLocations);

    @Mapping(source = "location.name", target = "location")
    @Mapping(source = "event.name", target = "eventName")
    EventLocationsDto toDto(EventsLocations eventsLocations);

    default EventsLocations toDto(Event event, EventLocationData data, Location location) {
        EventsLocations eventsLocation = new EventsLocations();
        eventsLocation.setEvent(event);
        eventsLocation.setLocation(location);
        eventsLocation.setDate(data.getDate());
        eventsLocation.setTotalTickets(data.getAvailableTickets());
        eventsLocation.setPrice(data.getPrice());
        eventsLocation.setEventLocationStatus(AVAILABLE);
        return eventsLocation;
    }
}
