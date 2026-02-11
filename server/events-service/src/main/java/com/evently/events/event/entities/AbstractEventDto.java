package com.evently.events.event.entities;

import com.evently.events.category.entities.CategoryDto;
import com.evently.events.artists.Artist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractEventDto {
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    @JsonIgnoreProperties("events")
    private Artist artist;
    @Setter(AccessLevel.PUBLIC)
    private List<CategoryDto> categoryDtoList;

    public AbstractEventDto(long id, String name, String description, String imageUrl, Artist artist) {
        setId(id);
        setName(name);
        setDescription(description);
        setImageUrl(imageUrl);
        setArtist(artist);
    }
}