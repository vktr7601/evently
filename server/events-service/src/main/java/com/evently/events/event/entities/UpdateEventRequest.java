package com.evently.events.event.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest implements Serializable {
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("categories")
    List<Long> categories;
    @JsonProperty("eventLocations")
    public List<EventsLocationsData> eventLocations;
}