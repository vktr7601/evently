package com.evently.events.artists.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArtistDetailsdDto extends AbstractArtistDto {
    private List<EventsLocationsDto> locations;

    public ArtistDetailsdDto(long id, String name, String bio, String imageUrl) {
        super(id, name, bio, imageUrl);
    }
}