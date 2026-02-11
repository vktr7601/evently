package com.evently.events.event.entities;

import com.evently.events.artists.Artist;

public class EventListDto extends AbstractEventDto {
    public EventListDto(long id, String name, String description, String imageUrl, Artist artist) {
        super(id, name, description, imageUrl, artist);
    }
}