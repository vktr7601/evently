package com.evently.events.artists.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractArtistDto {
    private String name;
    private String bio;
    private String imageUrl;
    private long id;

    public AbstractArtistDto(long id, String name, String bio, String imageUrl) {
        setId(id);
        setName(name);
        setBio(bio);
        setImageUrl(imageUrl);
    }
}