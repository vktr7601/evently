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
@AllArgsConstructor
@NoArgsConstructor
public class EventSeed implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("description")
    private String description;
    @JsonProperty("artistId")
    private long artistId;
    @JsonProperty("categoryIds")
    private List<Long> categoryIds;
}