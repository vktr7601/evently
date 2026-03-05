package com.evently.events.artists.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistSeed implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("imageUrl")
    private String imageUrl;
}