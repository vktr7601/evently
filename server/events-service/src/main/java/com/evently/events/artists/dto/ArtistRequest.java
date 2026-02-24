package com.evently.events.artists.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ArtistRequest {
    private String name;
    private String bio;
    MultipartFile image;
}