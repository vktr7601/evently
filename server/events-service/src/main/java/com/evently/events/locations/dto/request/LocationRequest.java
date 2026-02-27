package com.evently.events.locations.dto.request;

import com.evently.events.config.validator.MaxFileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LocationRequest {
    private String name;
    private String description;
    @MaxFileSize(maxSizeInMB = 2, message = "Image must be under 2MB")
    MultipartFile imageUrl;
}