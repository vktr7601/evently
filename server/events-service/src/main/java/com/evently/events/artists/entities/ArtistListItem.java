package com.evently.events.artists.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter(PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ArtistListItem implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("imageUrl")
    private String imageUrl;
}