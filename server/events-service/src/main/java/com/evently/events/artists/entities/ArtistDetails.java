package com.evently.events.artists.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Getter
@Setter(PRIVATE)
@NoArgsConstructor
public class ArtistDetails implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @Setter(PUBLIC)
    @JsonProperty("eventLocations")
    private List<EventsLocationsDto> eventLocations;

    public ArtistDetails(long id, String name, String bio, String imageUrl) {
        setId(id);
        setName(name);
        setBio(bio);
        setImageUrl(imageUrl);
    }
}