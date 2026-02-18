package com.evently.events.event.entities;

import com.evently.events.artists.Artist;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Added for Jackson/Redis
public class EventDetailDto implements Serializable {
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    @JsonIgnoreProperties("events")
    private Artist artist;
    @Setter(AccessLevel.PUBLIC)
    @JsonProperty("categories")
    private List<CategoryDto> categoryDtoList;

    @Setter(AccessLevel.PUBLIC)
    @JsonProperty("eventLocations")
    private List<EventsLocationsDto> eventLocationData;

    public EventDetailDto(long id, String name, String description, String imageUrl, Artist artist) {
        setId(id);
        setName(name);
        setDescription(description);
        setImageUrl(imageUrl);
        setArtist(artist);
    }
}