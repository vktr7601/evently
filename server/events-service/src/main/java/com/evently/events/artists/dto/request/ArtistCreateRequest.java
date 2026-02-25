package com.evently.events.artists.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ArtistCreateRequest {
    private String name;
    private String bio;
    MultipartFile image;
}