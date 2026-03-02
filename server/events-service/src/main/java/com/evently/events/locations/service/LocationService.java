package com.evently.events.locations.service;

import com.evently.events.eventsLocations.dto.request.EventsLocationsData;
import com.evently.events.infrastructure.clients.BookingServiceClient;
import com.evently.events.infrastructure.s3.S3Folders;
import com.evently.events.infrastructure.s3.S3Service;
import com.evently.events.locations.dto.LocationDetails;
import com.evently.events.locations.dto.LocationListItem;
import com.evently.events.locations.dto.LocationSeed;
import com.evently.events.locations.dto.mapper.LocationsMapper;
import com.evently.events.locations.dto.request.LocationRequest;
import com.evently.events.locations.model.Location;
import com.evently.events.locations.repository.LocationRepository;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final S3Service s3Service;
    private final LocationsMapper locationsMapper;
    private final BookingServiceClient bookingServiceClient;

    @Transactional
    public List<LocationListItem> getAll() {
        log.info("Starting process to fetch all locations.");

        List<LocationListItem> allLocations =
                locationRepository.findAllLocationItems();
        log.info("Found {} raw location entities in database.",
                allLocations.size());

        if (allLocations.isEmpty()) {
            log.info("No locations found in the database. Returning an empty " +
                    "list.");
            return List.of();
        }

        return allLocations;
    }


    //used to render the data in the frontend
    public LocationDetails findLocationDetails(long id) {
        LocationDetails locationDetails =
                locationRepository.findLocationDtoById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Location " +
                                        "with id " + id + " not found"));
        return locationDetails;
    }


    @Transactional
    public List<Location> findAllByNameIn(List<String> locationNames) {
        if (locationNames.isEmpty()) {
            return Collections.emptyList();
        }

        List<Location> locations =
                locationRepository.findAllByNameIn(locationNames);

        if (locations.size() != locationNames.size()) {
            log.warn("Some locations were not found for the provided names: " +
                    "{}", locationNames);
            throw new ResourceNotFoundException("Some locations were not " +
                    "found for the provided names: " + locationNames);
        }

        return locations;
    }

    public List<Location> findAllByIdIn(List<Long> locationIds) {
        if (locationIds.isEmpty()) {
            return new ArrayList<>();
        }

        return locationRepository.findAllByIdIn(locationIds);
    }


    public List<Location> findAllByEventLocationsData(List<EventsLocationsData> eventsLocationsData) {
        List<Long> locationIds = eventsLocationsData.stream()
                .map(EventsLocationsData::getLocationId)
                .toList();

        return locationRepository.findAllById(locationIds);
    }

    @Transactional
    public LocationDetails createLocation(LocationRequest locationRequest) {
        String imageUrl = s3Service.uploadFile(locationRequest.getImageUrl(),
                S3Folders.LOCATIONS);

        Location location = locationsMapper.toEntity(locationRequest, imageUrl);

        locationRepository.save(location);

        return locationsMapper.toDto(location);
    }

    @Transactional
    public void seedLocations(List<LocationSeed> seedList) {
        List<Location> locationList = new ArrayList<>();

        for (LocationSeed locationSeed : seedList) {
            if (!locationRepository.existsByName(locationSeed.getName())) {
                locationList.add(locationsMapper.toEntity(locationSeed));
            }
        }

        locationRepository.saveAll(locationList);
    }
}