package com.evently.events.locations.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LocationRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    MultipartFile imageUrl;
}