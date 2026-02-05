package com.evently.events.eventsVenues.entities;

import com.evently.events.event.Event;
import com.evently.events.event.entities.EventLocationData;
import com.evently.events.eventsVenues.EventsVenues;
import com.evently.events.venues.Venue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.evently.events.eventsVenues.EventsVenuesStatus.AVAILABLE;

@Mapper(componentModel = "spring")
public interface EventsLocationMapper {

    EventsVenues toEntity(EventsVenues eventsVenues);

    @Mapping(source = "venue.name", target = "venue")
    @Mapping(source = "event.name", target = "eventName")
    EventsVenuesDto toDto(EventsVenues eventsVenues);

    default EventsVenues toDto(Event event, EventLocationData data, Venue venue) {
        EventsVenues eventsLocation = new EventsVenues();
        eventsLocation.setEvent(event);
        eventsLocation.setVenue(venue);
        eventsLocation.setDate(data.getDate());
        eventsLocation.setTotalTickets(data.getAvailableTickets());
        eventsLocation.setPrice(data.getPrice());
        eventsLocation.setEventsVenuesStatus(AVAILABLE);
        return eventsLocation;
    }
}