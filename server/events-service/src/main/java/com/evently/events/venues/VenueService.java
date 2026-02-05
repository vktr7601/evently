package com.evently.events.venues;

import com.evently.events.event.entities.EventDto;
import com.evently.events.eventsVenues.EventsVenuesRepository;
import com.evently.events.venues.entities.VenueDetailsDto;
import com.evently.events.venues.entities.VenueDto;
import com.evently.events.venues.entities.VenueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {
    private final VenueRepository venueRepository;
    private final EventsVenuesRepository eventsVenuesRepository;
    private final VenueMapper venueMapper;

    public List<VenueDto> findAll() {
        List<Venue> allVenues = venueRepository.findAll();
        return allVenues.stream().map(venueMapper::toDto).toList();
    }

    public VenueDetailsDto getVenueEvents(long id) {
        Venue venue = venueRepository.findByIdOrThrow(id);
        List<EventDto> allByEventId = eventsVenuesRepository.findAllByVenueId(id);
        return new VenueDetailsDto(venue.getName(), venue.getImageUrl(), allByEventId);
    }
}