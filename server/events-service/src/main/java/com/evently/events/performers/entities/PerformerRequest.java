package com.evently.events.performers.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PerformerRequest {
    private String name;
    private String bio;
    MultipartFile image;
}