package com.evently.events.locations.dto.mapper;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.model.EventsLocations;
import com.evently.events.locations.dto.LocationDetails;
import com.evently.events.locations.dto.request.LocationRequest;
import com.evently.events.locations.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationsMapper {


    public Location toEntity(LocationRequest locationRequest, String imageUrl) {
        Location location = new Location();
        location.setName(locationRequest.getName());
        location.setImageUrl(imageUrl);
        location.setDescription(locationRequest.getDescription());


        return location;
    }

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

    public LocationDetails toDto(Location entity) {
        LocationDetails locationDetails = new LocationDetails();
        locationDetails.setDescription(entity.getDescription());
        locationDetails.setName(entity.getName());
        locationDetails.setImageUrl(entity.getImageUrl());
        locationDetails.setId(entity.getId());

        return locationDetails;
    }
}