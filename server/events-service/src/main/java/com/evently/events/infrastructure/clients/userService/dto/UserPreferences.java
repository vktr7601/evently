package com.evently.events.infrastructure.clients.userService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferences implements Serializable {
    @JsonProperty("categories")
    private List<Long> categories;
    @JsonProperty("locations")
    private List<Long> locations;
    @JsonProperty("artists")
    private List<Long> artists;
}