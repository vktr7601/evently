package com.evently.events.locations;

import com.evently.events.eventsLocations.EventsLocationsService;
import com.evently.events.eventsLocations.entities.FetchMode;
import com.evently.events.locations.entities.LocationDetails;
import com.evently.events.locations.entities.LocationListItem;
import com.evently.events.locations.entities.LocationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final EventsLocationsService eventsLocationsService;

    @GetMapping
    public ResponseEntity<List<LocationListItem>> getAll() {
        log.info("Received request to fetch all location items for listing.");

        List<LocationListItem> locationListItems = locationService.getAll();

        log.info("Successfully retrieved {} location items.",
                locationListItems.size());

        return ResponseEntity.ok(locationListItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDetails> getById(@PathVariable Long id) {
        log.info("Received request to fetch location details for location " +
                "with id {}.", id);
        LocationDetails locationDetails =
                eventsLocationsService.findLocationDetails(id,
                        FetchMode.WITH_AVAILABILITY);

        log.info("Successfully fetched location details for location with id " +
                "{}.", id);

        return ResponseEntity.ok(locationDetails);
    }

    @PostMapping
    public ResponseEntity<?> createLocation(@RequestBody LocationRequest locationRequest) {
        System.out.println();

        return null;
    }
}