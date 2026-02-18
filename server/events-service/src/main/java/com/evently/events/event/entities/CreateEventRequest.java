package com.evently.events.event.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ValidCreateEvent
public class CreateEventRequest {
    @JsonProperty("eventName")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("eventLocations")
    @Valid
    public List<EventsLocationsData> eventLocations = new ArrayList<>();
    @JsonProperty("categories")
    public List<Long> categories = new ArrayList<>();
    @JsonProperty("artistId")
    public Long artistId;
}