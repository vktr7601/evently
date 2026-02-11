package com.evently.events.eventsLocations.entities;

import com.evently.events.event.Event;
import com.evently.events.event.entities.EventLocationData;
import com.evently.events.eventsLocations.EventsLocations;
import com.evently.events.locations.Location;
import org.mapstruct.Mapper;

import static com.evently.events.eventsLocations.entities.EventsLocationsStataus.AVAILABLE;

@Mapper(componentModel = "spring")
public interface EventsLocationMapper {

    EventsLocations toEntity(EventsLocations eventsVenues);

//    @Mapping(source = "venue.name", target = "venue")
//    @Mapping(source = "event.name", target = "eventName")
//    @Mapping(source = "venue.id", target = "venueId")
//    EventsVenuesDto toDto(EventsVenues eventsVenues);

    default EventsLocations toDto(Event event, EventLocationData data, Location location) {
        EventsLocations eventsLocation = new EventsLocations();
        eventsLocation.setEvent(event);
        eventsLocation.setLocation(location);
        eventsLocation.setDate(data.getDate());
        eventsLocation.setTotalTickets(data.getAvailableTickets());
        eventsLocation.setPrice(data.getPrice());
        eventsLocation.setEventsLocationsStataus(AVAILABLE);
        return eventsLocation;
    }
}