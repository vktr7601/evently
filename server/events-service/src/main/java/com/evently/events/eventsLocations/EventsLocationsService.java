package com.evently.events.eventsLocations;


import com.evently.events.event.Event;
import com.evently.events.event.entities.EventLocationData;
import com.evently.events.eventsLocations.entities.EventsLocationMapper;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.locations.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EventsLocationsService {
    private final EventsLocationsRepository eventsLocationsRepository;
    private final EventsLocationMapper eventsLocationMapper;

    public List<EventsLocationsDto> addLocationDetails(Event event, List<EventLocationData> eventLocationData, Map<Long, Location> locationMap) {
        List<EventsLocations> eventsLocationsList = eventLocationData.stream()
            .map(eventLocat -> eventsLocationMapper.toEntity(event, eventLocat, locationMap.get(eventLocat.getLocationId())))
            .toList();

        List<EventsLocations> eventsLocations = eventsLocationsRepository.saveAll(eventsLocationsList);

        List<EventsLocationsDto> mappedEntities = eventsLocations.stream().map(eventsLocationMapper::toDto).toList();

        return mappedEntities;
    }


    public List<EventsLocationsDto> findUpcomingEventLocationsByEventId(long eventId) {
        log.info("Fetching upcoming locations for event ID: {}", eventId);

        List<EventsLocationsDto> locations = eventsLocationsRepository.findUpcomingEventLocationsByEventId(eventId);

        log.info("Found upcoming locations for event ID: {}", eventId);

        return locations;
    }

    public List<EventsLocationsDto> findAllUpcomingEventsByLocationId(long locationId) {
        log.info("Fetching all upcoming events for location ID: {}", locationId);

        List<EventsLocationsDto> events = eventsLocationsRepository.findAllUpcomingEventsByLocationId(locationId);

        return events;
    }


    public List<EventsLocationsDto> findAllUpcomingEventsByArtistId(long artistId) {
        log.info("Fetching all upcoming events for artist ID: {}", artistId);

        List<EventsLocationsDto> events = eventsLocationsRepository.findAllUpcomingEventsByArtistId(artistId);

        return events;
    }
}