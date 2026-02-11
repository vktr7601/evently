package com.evently.events.event.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.artists.Artist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
public class EventDetailsDto extends AbstractEventDto {
    //used in event/1
    @Setter(AccessLevel.PUBLIC)
    private List<EventsLocationsDto> eventLocationData;

    public EventDetailsDto(long id, String name, String description, String imageUrl, Artist artist) {
        super(id, name, description, imageUrl, artist);
    }
}