package com.evently.events.event.entities;

import com.evently.events.artists.model.Artist;
import com.evently.events.category.entities.CategoryDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventListItemDto implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @JsonIgnoreProperties("events")
    private Artist artist;
    @Setter(AccessLevel.PUBLIC)
    @JsonProperty("categories")
    private List<CategoryDto> categoryDtoList;

    public EventListItemDto(long id, String name, String description, String imageUrl, Artist artist) {
        setId(id);
        setName(name);
        setDescription(description);
        setImageUrl(imageUrl);
        setArtist(artist);
    }
}