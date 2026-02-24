package com.evently.users.follow.artist.dto;

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
public class FollowArtistResponse implements Serializable {
    @JsonProperty("artistId")
    private long artistId;
    @JsonProperty("status")
    private String status;
}