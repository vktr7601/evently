package com.evently.events.venues;

import com.evently.events.venues.entities.LocationDetailsDto;
import com.evently.events.venues.entities.LocationDto;
import com.evently.events.venues.entities.LocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/venues")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllVenues() {
        List<LocationDto> locationDtos = locationService.findAll();
        return ResponseEntity.ok(locationDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDetailsDto> getVenueDetails(@PathVariable long id) {
        return ResponseEntity.ok(locationService.getVenueEvents(id));
    }

    @PostMapping
    public ResponseEntity<LocationDto> create(@ModelAttribute LocationRequest locationRequest) {
        LocationDto locationDto = locationService.saveVanue(locationRequest);
        return new ResponseEntity<>(locationDto, HttpStatus.CREATED);
    }
}