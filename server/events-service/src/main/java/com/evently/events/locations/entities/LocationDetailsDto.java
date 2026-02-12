package com.evently.events.locations.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class LocationDetailsDto extends AbstractLocationDto {
    @Setter(AccessLevel.PUBLIC)
    private List<EventsLocationsDto> eventlocationsdto;

    public LocationDetailsDto(long id, String name, String description, String imageUrl) {
        super(id, name, description, imageUrl);
    }
}