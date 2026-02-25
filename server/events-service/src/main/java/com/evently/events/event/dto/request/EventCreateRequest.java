package com.evently.events.event.dto.request;

import com.evently.events.event.validation.ValidCreateEvent;
import com.evently.events.eventsLocations.dto.request.EventsLocationsData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ValidCreateEvent
public class EventCreateRequest {
    @JsonProperty("eventName")
    public String name;
    @JsonProperty("eventDescription")
    public String description;
    @JsonProperty("eventLocations")
    @Valid
    public List<EventsLocationsData> eventLocations = new ArrayList<>();
    @JsonProperty("eventCategories")
    public List<Long> categories = new ArrayList<>();
    @JsonProperty("artistId")
    public Long artistId;
}