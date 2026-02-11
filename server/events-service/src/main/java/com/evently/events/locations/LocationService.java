package com.evently.events.locations;

import com.evently.events.eventsLocations.EventsLocationsService;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.locations.entities.LocationDetailsDto;
import com.evently.events.locations.entities.LocationListItemDto;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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