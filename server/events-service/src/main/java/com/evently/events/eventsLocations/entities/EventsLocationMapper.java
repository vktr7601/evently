package com.evently.events.eventsLocations.entities;

import com.evently.events.event.Event;
import com.evently.events.event.entities.EventLocationData;
import com.evently.events.eventsLocations.EventsLocations;
import com.evently.events.locations.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventsLocationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "data.date", target = "date")
    @Mapping(source = "data.tickets", target = "totalTickets")
    @Mapping(source = "data.price", target = "price")
    @Mapping(source = "event", target = "event")
    @Mapping(source = "location", target = "location")
    @Mapping(target = "eventsLocationsStataus", constant = "AVAILABLE")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    EventsLocations toEntity(Event event, EventLocationData data, Location location);


    @Mapping(source = "id", target = "eventLocationId")
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "event.name", target = "eventName")
    @Mapping(source = "location.name", target = "locationName")
    @Mapping(source = "date", target = "eventStartTime")
    @Mapping(source = "price", target = "pricePerTicket")
    @Mapping(source = "eventsLocationsStataus", target = "status")
    @Mapping(source = "totalTickets", target = "ticketsCount")
    EventsLocationsDto toDto(EventsLocations entity);
}