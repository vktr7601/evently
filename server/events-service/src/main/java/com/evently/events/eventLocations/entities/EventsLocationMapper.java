package com.evently.events.eventLocations.entities;

import com.evently.events.event.Event;
import com.evently.events.event.entities.EventLocationData;
import com.evently.events.eventLocations.EventsLocations;
import com.evently.events.locations.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.evently.events.eventLocations.EventLocationStatus.AVAILABLE;

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