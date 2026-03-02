package com.evently.events.locations.controller;

import com.evently.events.eventsLocations.service.EventsLocationsService;
import com.evently.events.eventsLocations.service.data.FetchMode;
import com.evently.events.locations.dto.LocationDetails;
import com.evently.events.locations.dto.LocationListItem;
import com.evently.events.locations.dto.request.LocationRequest;
import com.evently.events.locations.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final EventsLocationsService eventsLocationsService;

    @GetMapping
    public ResponseEntity<List<LocationListItem>> getAll() {
        List<LocationListItem> locationListItems = locationService.getAll();

        return ResponseEntity.ok(locationListItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDetails> getById(@PathVariable Long id) {
        LocationDetails locationDetails =
                eventsLocationsService.findLocationDetails(id,
                        FetchMode.WITH_AVAILABILITY);

        return ResponseEntity.ok(locationDetails);
    }
}