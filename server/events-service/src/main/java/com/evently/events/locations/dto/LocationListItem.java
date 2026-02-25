package com.evently.events.locations.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

/**
 * Represents a summary of a location used when displaying the list of locations on the locations page.
 * Contains only the essential fields needed for list rendering: identifier, name, description, and image.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LocationListItem implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("imageUrl")
    private String imageUrl;
}