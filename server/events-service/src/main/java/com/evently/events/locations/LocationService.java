package com.evently.events.locations;

import com.evently.events.event.Event;
import com.evently.events.eventsLocations.EventsLocationsService;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.locations.entities.LocationDetailsDto;
import com.evently.events.locations.entities.LocationListItemDto;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final EventsLocationsService eventsLocationsService;

    public List<LocationListItemDto> findAllLocationItems() {
        log.info("Starting process to fetch all locations.");

        List<LocationListItemDto> allLocations = locationRepository.findAllLocationItems();
        log.info("Found {} raw location entities in database.", allLocations.size());

        if (allLocations.isEmpty()) {
            log.info("No locations found in the database. Returning an empty list.");
            return List.of();
        }

        return allLocations;
    }

    public LocationDetailsDto findLocationDetailsById(long id) {
        LocationDetailsDto locationDetailsDto = locationRepository.findLocationDtoById(id).orElseThrow(() -> new ResourceNotFoundException("Location with id " + id + " not found"));

        List<EventsLocationsDto> eventlocationsdto = eventsLocationsService.findAllUpcomingEventsByLocationId(id);

        locationDetailsDto.setEventlocationsdto(eventlocationsdto);

        return locationDetailsDto;
    }

    public void addLocations(Event event, List<EventsLocationsDto> eventsLocationsDtos) {
        List<Long> locationsId = eventsLocationsDtos.stream().map(EventsLocationsDto::getLocationId).toList();
        Map<Long, Location> locationMap = findAllByIdIn(locationsId);

    }

    @Transactional
    public List<Location> findAllByNameIn(List<String> locationNames) {
        if (locationNames.isEmpty()) {
            return Collections.emptyList();
        }

        List<Location> locations = locationRepository.findAllByNameIn(locationNames);

        if (locations.size() != locationNames.size()) {
            log.warn("Some locations were not found for the provided names: {}", locationNames);
            throw new ResourceNotFoundException("Some locations were not found for the provided names: " + locationNames);
        }

        return locations;
    }


    public Map<Long, Location> findAllByIdIn(List<Long> locationIds) {
        if (locationIds.isEmpty()) {
            return new HashMap<>();
        }

        List<Location> locations = locationRepository.findAllByIdIn(locationIds);

        if (locations.size() != locationIds.size()) {
            log.warn("Some locations were not found for the provided IDs: {}", locationIds);
            throw new ResourceNotFoundException("Some locations were not found for the provided IDs: " + locationIds);
        }

        Map<Long, Location> locationMap = locations.stream()
            .collect(Collectors.toMap(Location::getId, location -> location));

        return locationMap;
    }

//    public LocationDetailsDto getVenueEvents(long id) {
//        Location venue = locationRepository.findByIdOrThrow(id);
//        // var allByEventId = eventsVenuesRepository.findAllByVenueId(id);
//        return null;
////        return new VenueDetailsDto(venue.getName(), venue.getImageUrl(), allByEventId);
//    }
//
//    public LocationListItemDto saveVanue(LocationRequest locationRequest) {
//        String imageUrl = "";
//        try {
//            imageUrl = s3BucketService.uploadFile(locationRequest.getImageUrl());
//            Location location = locationMapper.toEntity(locationRequest);
//            location.setImageUrl(imageUrl);
//            Location savedEntity = locationRepository.save(location);
//            return locationMapper.toDto(savedEntity);
//        } catch (Exception e) {
//            s3BucketService.deleteFile(imageUrl);
//        }
//        return null;
//    }
}