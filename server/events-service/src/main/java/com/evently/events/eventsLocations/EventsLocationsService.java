package com.evently.events.eventsLocations;


import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EventsLocationsService {
    private final EventsLocationsRepository eventsVenuesRepository;

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

    public List<EventsLocationsDto> findUpcomingEventLocationsByEventId(long eventId) {
        log.info("Fetching upcoming locations for event ID: {}", eventId);

        List<EventsLocationsDto> locations = eventsVenuesRepository.findUpcomingEventLocationsByEventId(eventId);

        log.info("Found upcoming locations for event ID: {}", eventId);

        return locations;
    }

    public List<EventsLocationsDto> findAllUpcomingEventsByLocationId(long locationId) {
        log.info("Fetching all upcoming events for location ID: {}", locationId);

        List<EventsLocationsDto> events = eventsVenuesRepository.findAllUpcomingEventsByLocationId(locationId);

        return events;
    }
}