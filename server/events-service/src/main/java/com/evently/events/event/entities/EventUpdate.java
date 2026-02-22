package com.evently.events.event.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
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
public class EventUpdate implements Serializable {
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("eventDescription")
    private String eventDescription;
    @JsonProperty("eventCategories")
    List<Long> categories;
    @Valid
    @JsonProperty("eventLocations")
    public List<EventsLocationsData> eventLocations;
}