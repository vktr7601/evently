package com.evently.events.artists.dto;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDetails implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @JsonProperty("eventLocations")
    private List<EventsLocationsDto> eventLocations;
}