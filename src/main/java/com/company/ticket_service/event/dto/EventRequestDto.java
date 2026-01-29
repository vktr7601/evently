package com.company.ticket_service.event.dto;

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
    @JsonProperty("locations")
    public List<EventLocationData> eventLocationData;
    @JsonProperty("classification")
    List<String> classifications;
}
