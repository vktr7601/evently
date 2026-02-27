package com.evently.events.locations.controller;

import com.evently.events.locations.dto.LocationDetails;
import com.evently.events.locations.dto.request.LocationRequest;
import com.evently.events.locations.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/location")
public class AdminLocationsController {
    private final LocationService locationService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LocationDetails> createLocation(@Valid @ModelAttribute LocationRequest locationRequest) {
        LocationDetails locationDetails =
                locationService.createLocation(locationRequest);

        return new ResponseEntity(locationDetails, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateExistingLocation() {
        return null;
    }
}