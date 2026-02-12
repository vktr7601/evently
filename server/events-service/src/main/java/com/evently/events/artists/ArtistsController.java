package com.evently.events.artists;

import com.evently.events.artists.entities.ArtistDetailsdDto;
import com.evently.events.artists.entities.ArtistListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<ArtistDetailsdDto> getArtistDetails(@PathVariable Long id) {
        ArtistDetailsdDto artistDetailsdDto = artistsService.findArtistDetails(id);

        return ResponseEntity.ok(artistDetailsdDto);
    }
}