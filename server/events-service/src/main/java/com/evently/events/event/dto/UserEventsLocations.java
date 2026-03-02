package com.evently.events.event.dto;

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
public class UserEventsLocations implements Serializable {
    @JsonProperty("basedOnCategories")
    private List<EventListItemDto> basedOnCategory;
    @JsonProperty("basedOnLocations")
    private List<EventListItemDto> basedOnLocation;
    @JsonProperty("basedOnArtist")
    private List<EventListItemDto> basedOnArtist;
}