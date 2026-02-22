package com.evently.events.locations.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the full details of a location used when displaying the
 * location details page.
 * Extends the basic location information with a list of associated event
 * locations.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LocationDetails implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @Setter(AccessLevel.PUBLIC)
    @JsonProperty("eventLocations")
    private List<EventsLocationsDto> eventsLocations;

//    public LocationDetails(long id, String name, String description, String
//    imageUrl) {
//        setId(id);
//        setName(name);
//        setDescription(description);
//        setImageUrl(imageUrl);
//    }
}