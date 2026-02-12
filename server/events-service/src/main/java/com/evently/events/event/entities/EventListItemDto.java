package com.evently.events.event.entities;

import com.evently.events.artists.Artist;

public class EventListItemDto extends AbstractEventDto {
    public EventListItemDto(long id, String name, String description, String imageUrl, Artist artist) {
        super(id, name, description, imageUrl, artist);
    }
}