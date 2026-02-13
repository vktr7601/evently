package com.evently.events.event.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventRequestDto {
    @JsonProperty("event_name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("eventLocations")
    public List<EventLocationData> eventLocations;
    @JsonProperty("categories")
    List<Long> categories;
    @JsonProperty("artist_id")
    long artistId;
}