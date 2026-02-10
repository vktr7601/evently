package com.evently.events.venues;

import com.evently.events.venues.entities.VenueDetailsDto;
import com.evently.events.venues.entities.VenueDto;
import com.evently.events.venues.entities.VenueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<VenueDto> create(@ModelAttribute VenueRequest venueRequest) {
        VenueDto venueDto = venueService.saveVanue(venueRequest);
        return new ResponseEntity<>(venueDto, HttpStatus.CREATED);
    }
}