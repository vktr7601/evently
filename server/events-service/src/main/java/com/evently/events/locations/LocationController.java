package com.evently.events.locations;

import com.evently.events.locations.entities.LocationDetailsDto;
import com.evently.events.locations.entities.LocationListItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<LocationListItemDto>> getLocationListItems() {
        log.info("Received request to fetch all location items for listing.");

        List<LocationListItemDto> locationListItemDtos = locationService.findAllLocationItems();

        log.info("Successfully retrieved {} location items.", locationListItemDtos.size());

        return ResponseEntity.ok(locationListItemDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDetailsDto> getLocationDetails(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findLocationDetailsById(id));
    }
}