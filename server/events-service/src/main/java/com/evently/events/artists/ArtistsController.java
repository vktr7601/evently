package com.evently.events.artists;

import com.evently.events.artists.entities.ArtistDetails;
import com.evently.events.artists.entities.ArtistListItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistsController {
    private final ArtistsService artistsService;

    @GetMapping
    public ResponseEntity<List<ArtistListItem>> getAllPerformers() {
        List<ArtistListItem> artistListItems = artistsService.findAllArtistsSortedByDateDesc();
        return ResponseEntity.ok(artistListItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDetails> getArtistDetails(@PathVariable Long id) {
        log.info("Request to get artist details by id {}", id);
        ArtistDetails artistDetailsDto = artistsService.findArtistDetails(id);

        return ResponseEntity.ok(artistDetailsDto);
    }
}