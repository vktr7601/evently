package com.evently.events.locations.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AbstractLocationDto {
    private long id;
    private String name;
    private String description;
    private String imageUrl;

    protected AbstractLocationDto(long id, String name, String description, String imageUrl) {
        setId(id);
        setName(name);
        setDescription(description);
        setImageUrl(imageUrl);
    }
}