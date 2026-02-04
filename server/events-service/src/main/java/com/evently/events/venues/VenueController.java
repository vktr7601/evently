package com.evently.events.venues;

import com.evently.events.venues.entities.VenueDetailsDto;
import com.evently.events.venues.entities.VenueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {
    private final VenueService venueService;

    @GetMapping
    public ResponseEntity<List<VenueDto>> getAllVenues() {
        List<VenueDto> venueDtos = venueService.findAll();
        return ResponseEntity.ok(venueDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDetailsDto> getVenueDetails(@PathVariable long id) {
        return ResponseEntity.ok(venueService.getVenueEvents(id));
    }
}