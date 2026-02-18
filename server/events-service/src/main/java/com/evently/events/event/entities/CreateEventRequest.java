package com.evently.events.event.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateEventRequest {
    @NotBlank(message = "Please, provide an event name.")
    @Size(min = 3, max = 100, message = "Event name must be between 3 and 100 characters")
    @JsonProperty("event_name")
    public String name;

    @NotBlank(message = "Please, provide a event description.")
    @Size(min = 10, message = "Description should be at least 10 characters")
    @JsonProperty("description")
    public String description;

    @NotEmpty(message = "At least one location must be provided")
    @Valid
    @JsonProperty("eventLocations")
    public List<EventsLocationsData> eventLocations = new ArrayList<>();

    @NotEmpty(message = "At least one category must be selected")
    @JsonProperty("categories")
    public List<Long> categories = new ArrayList<>();

    @NotNull(message = "Please, provide an artist from the select box.")
    @Min(value = 1, message = "Invalid Artist ID")
    @JsonProperty("artist_id")
    public Long artistId;
}