package com.evently.events.venues.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class VenueRequest {
    private String name;
    MultipartFile imageUrl;
}