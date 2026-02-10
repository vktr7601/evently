package com.evently.events.venues;

import com.evently.events.config.S3BucketService;
import com.evently.events.event.entities.EventDto;
import com.evently.events.eventsVenues.EventsVenuesRepository;
import com.evently.events.venues.entities.VenueDetailsDto;
import com.evently.events.venues.entities.VenueDto;
import com.evently.events.venues.entities.VenueMapper;
import com.evently.events.venues.entities.VenueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {
    private final VenueRepository venueRepository;
    private final EventsVenuesRepository eventsVenuesRepository;
    private final VenueMapper venueMapper;
    private final S3BucketService s3BucketService;


    public List<VenueDto> findAll() {
        List<Venue> allVenues = venueRepository.findAll();
        return allVenues.stream().map(venueMapper::toDto).toList();
    }

    public VenueDetailsDto getVenueEvents(long id) {
        Venue venue = venueRepository.findByIdOrThrow(id);
        List<EventDto> allByEventId = eventsVenuesRepository.findAllByVenueId(id);
        return new VenueDetailsDto(venue.getName(), venue.getImageUrl(), allByEventId);
    }

    public VenueDto saveVanue(VenueRequest venueRequest) {
        String imageUrl = "";
        try {
            imageUrl = s3BucketService.uploadFile(venueRequest.getImageUrl());
            Venue venue = venueMapper.toEntity(venueRequest);
            venue.setImageUrl(imageUrl);
            Venue savedEntity = venueRepository.save(venue);
            return venueMapper.toDto(savedEntity);
        } catch (Exception e) {
            s3BucketService.deleteFile(imageUrl);
        }
        return null;
    }
}