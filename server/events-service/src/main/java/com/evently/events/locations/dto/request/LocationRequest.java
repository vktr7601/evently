package com.evently.events.locations.dto.request;

import com.evently.events.config.validator.MaxFileSize;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest implements Serializable {
    @NotNull
    @NotEmpty
    @JsonProperty("name")
    private String name;
    @NotNull
    @NotEmpty
    @JsonProperty("description")
    private String description;
    @MaxFileSize(maxSizeInMB = 2, message = "Image must be under 2MB")
    MultipartFile imageUrl;
}