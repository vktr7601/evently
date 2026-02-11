package com.evently.events.eventsLocations;


import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.entities.EventsLocationMapper;
import com.evently.events.venues.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventsLocationsService {
    private final EventsLocationsRepository eventsVenuesRepository;
    private final LocationRepository locationRepository;
    private final EventsLocationMapper eventsLocationMapper;

//    public List<EventsVenuesDto> create(Event event, List<EventLocationData> eventLocationData) {
//        List<String> locationsNames = eventLocationData.stream().map(EventLocationData::getVenue).toList();
//        Map<String, Venue> map = venueRepository.findAllByNameIn(locationsNames).stream().collect(Collectors.toMap(Venue::getName, Function.identity()));
//
//        List<EventsVenues> list = eventLocationData.stream().map(data -> eventsLocationMapper.toDto(event, data, map.get(data.getVenue()))).toList();
//
//        eventsVenuesRepository.saveAll(list);
//
//        return list.stream().map(eventsLocationMapper::toDto).toList();
//    }

//    public List<EventOccurrenceDTO> getEventsByLocation(String location) {
//        return eventsVenuesRepository.finaAllByLocationName(location);
//    }

    public List<EventsLocationsDto> findUpcomingVenuesByEventId(long eventId) {
        return eventsVenuesRepository.findUpcomingVenuesByEventId(eventId);
    }
}