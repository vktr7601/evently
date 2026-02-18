package com.evently.events.eventsLocations;


import com.evently.events.event.Event;
import com.evently.events.eventsLocations.entities.EventsLocationMapper;
import com.evently.events.eventsLocations.entities.EventsLocationsData;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.entities.EventsLocationsStatus;
import com.evently.events.exceptions.LocationCollisionException;
import com.evently.events.locations.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EventsLocationsService {
    private final EventsLocationsRepository eventsLocationsRepository;
    private final EventsLocationMapper eventsLocationMapper;
    private final BookingServiceClient bookingServiceClient;

    @Transactional
    public List<EventsLocationsDto> addLocationDetails(Event event, List<EventsLocationsData> eventLocationData, Map<Long, Location> locationMap) {
        checkCollisions(eventLocationData, locationMap);
        List<EventsLocations> eventsLocationsList = eventLocationData.stream()
            .map(eventLoc -> eventsLocationMapper.toEntity(event, eventLoc, locationMap.get(eventLoc.getLocationId())))
            .toList();

        List<EventsLocations> eventsLocations = eventsLocationsRepository.saveAll(eventsLocationsList);

        List<EventsLocationsDto> mappedEntities = eventsLocations.stream().map(eventsLocationMapper::toDto).toList();

        return mappedEntities;
    }

    private void checkCollisions(List<EventsLocationsData> eventLocationData, Map<Long, Location> locationMap) throws LocationCollisionException {
        List<String> collisions = new ArrayList<>();
        eventLocationData.forEach(x -> {
            LocalDate date = x.getEventDate().toLocalDate();
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            if (eventsLocationsRepository.hasEventForLocationInSpecificDate(x.getLocationId(), startOfDay, endOfDay)) {
                collisions.add("Event already exists for location: " + locationMap.get(x.getLocationId()).getName() + " on date: " + x.getEventDate());
            }
        });

        if (!collisions.isEmpty()) {
            throw new LocationCollisionException(collisions);
        }
    }

    public List<EventsLocationsDto> findUpcomingEventLocationsByEventId(long eventId) {
        log.info("Fetching upcoming locations for event ID: {}", eventId);

        List<EventsLocationsDto> locations = eventsLocationsRepository.findUpcomingEventLocationsByEventId(eventId);

        for (EventsLocationsDto location : locations) {
            try {
                var x = bookingServiceClient.checkAvailability(location.getEventLocationId(), 1);
                location.setEventsLocationsStatus(EventsLocationsStatus.AVAILABLE);
            } catch (Exception e) {
                location.setEventsLocationsStatus(EventsLocationsStatus.SOLD_OUT);
            }
        }

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

    public Map<Long, EventsLocationsDto> findAllByIdsAsMap(List<Long> eventsLocationsIds) {
        if (eventsLocationsIds.isEmpty()) {
            return new HashMap<>();
        }
        var eventsLocations = eventsLocationsRepository.findAllInList(eventsLocationsIds);

        System.out.println();
        Map<Long, EventsLocationsDto> locationsMap = eventsLocations.stream()
            .collect(Collectors.toMap(EventsLocationsDto::getEventLocationId,
                eventLocationDto -> eventLocationDto
            ));


        return locationsMap;
    }
}