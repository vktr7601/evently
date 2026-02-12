package com.evently.events.locations.entities;

import lombok.Getter;

@Getter
public class LocationListItemDto extends AbstractLocationDto {
    public LocationListItemDto(long id, String name, String description, String imageUrl) {
        super(id, name, description, imageUrl);
    }
}