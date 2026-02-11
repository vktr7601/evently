package com.evently.events.artists;

import com.evently.events.artists.entities.ArtistsDto;
import com.evently.events.artists.entities.ArtistRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistsController {
    private final ArtistsService artistsService;

    @GetMapping
    public ResponseEntity<List<ArtistsDto>> getAllPerformers() {
        List<ArtistsDto> artistsDtos = artistsService.findAll();
        return ResponseEntity.ok(artistsDtos);
    }

    @PostMapping
    public ResponseEntity<ArtistsDto> create(@ModelAttribute ArtistRequest request) {
        ArtistsDto artistsDto = artistsService.savePerformer(request);
        return new ResponseEntity<>(artistsDto, HttpStatus.CREATED);
    }
}