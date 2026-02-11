package com.evently.events.venues;

import com.evently.events.config.S3BucketService;
import com.evently.events.eventsLocations.EventsLocationsRepository;
import com.evently.events.venues.entities.LocationDetailsDto;
import com.evently.events.venues.entities.LocationDto;
import com.evently.events.venues.entities.LocationMapper;
import com.evently.events.venues.entities.LocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final EventsLocationsRepository eventsVenuesRepository;
    private final LocationMapper locationMapper;
    private final S3BucketService s3BucketService;


    public List<LocationDto> findAll() {
        List<Location> allLocations = locationRepository.findAll();
        return allLocations.stream().map(locationMapper::toDto).toList();
    }

    public LocationDetailsDto getVenueEvents(long id) {
        Location venue = locationRepository.findByIdOrThrow(id);
        // var allByEventId = eventsVenuesRepository.findAllByVenueId(id);
        return null;
//        return new VenueDetailsDto(venue.getName(), venue.getImageUrl(), allByEventId);
    }

    public LocationDto saveVanue(LocationRequest locationRequest) {
        String imageUrl = "";
        try {
            imageUrl = s3BucketService.uploadFile(locationRequest.getImageUrl());
            Location location = locationMapper.toEntity(locationRequest);
            location.setImageUrl(imageUrl);
            Location savedEntity = locationRepository.save(location);
            return locationMapper.toDto(savedEntity);
        } catch (Exception e) {
            s3BucketService.deleteFile(imageUrl);
        }
        return null;
    }
}