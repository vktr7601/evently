package com.evently.events.event.dto;

import com.evently.events.artists.model.Artist;
import com.evently.events.category.dto.CategoryDto;
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
    @JsonProperty("id")
    private long id;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("eventDescription")
    private String description;
    @JsonProperty("eventImageUrl")
    private String imageUrl;
    @JsonIgnoreProperties("events")
    @JsonProperty("artist")
    private Artist artist;
    @Setter(AccessLevel.PUBLIC)
    @JsonProperty("categories")
    private List<CategoryDto> categoryDtoList;

    @Setter(AccessLevel.PUBLIC)
    @JsonProperty("eventLocations")
    private List<EventsLocationsDto> eventsLocations;

    public EventDetailDto(long id, String eventName, String description, String imageUrl, Artist artist) {
        setId(id);
        setEventName(eventName);
        setDescription(description);
        setImageUrl(imageUrl);
        setArtist(artist);
    }
}