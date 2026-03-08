package com.evently.events.artists.controller;

import com.evently.events.artists.dto.ArtistDetails;
import com.evently.events.artists.dto.request.ArtistRequest;
import com.evently.events.artists.service.ArtistsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@RestController
@RequestMapping("/admin/artist")
@RequiredArgsConstructor
public class AdminArtistController {
    private final ArtistsService artistsService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtistDetails> createArtist(@Valid @ModelAttribute ArtistRequest artistRequest) {
        ArtistDetails artistDetails =
                artistsService.createArtist(artistRequest);

        return new ResponseEntity<>(artistDetails,
                HttpStatus.CREATED);
    }
}