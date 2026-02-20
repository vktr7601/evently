package com.evently.users.artistFollow.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistFollowRequest {
    @JsonProperty("artist_id")
    private long artistId;
    @JsonProperty("artist_name")
    private String artistName;
}