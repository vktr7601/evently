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
public class EventFeed implements Serializable {
    @JsonProperty("userId")
    private long userId;
    @JsonProperty("artistsEvent")
    private List<EventListItemDto> artists;
    @JsonProperty("isArtistUserDefined")
    private boolean isArtistUserDefined;
    @JsonProperty("categories")
    private List<EventListItemDto> categories;
    @JsonProperty("isCategoryUserDefined")
    private boolean isCategoryUserDefined;
    @JsonProperty("locations")
    private List<EventListItemDto> locations;
    @JsonProperty("isLocationUserDefined")
    private boolean isLocationUserDefined;
}