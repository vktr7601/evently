package com.evently.events.eventsLocations;


import com.evently.events.event.Event;
import com.evently.events.event.entities.EventLocationData;
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
    private final EventsLocationsRepository eventsLocationsRepository;

    public List<EventLocationData> addLocations(Event event, List<EventLocationData> eventLocationData) {
        List<String> locationsNames = eventLocationData.stream().map(EventLocationData::getLocation).toList();

return  null;


    }

//    public List<EventOccurrenceDTO> getEventsByLocation(String location) {
//        return eventsVenuesRepository.finaAllByLocationName(location);
//    }

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