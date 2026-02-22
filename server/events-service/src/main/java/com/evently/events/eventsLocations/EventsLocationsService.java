package com.evently.events.eventsLocations;


import com.evently.events.config.EventLocationsMapper;
import com.evently.events.event.Event;
import com.evently.events.eventsLocations.entities.EventsLocationsData;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.entities.EventsLocationsStatus;
import com.evently.events.eventsLocations.entities.FetchMode;
import com.evently.events.infra.exceptions.LocationCollisionException;
import com.evently.events.locations.Location;
import com.evently.events.locations.LocationService;
import com.evently.events.locations.entities.LocationDetails;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EventsLocationsService {
    private final EventsLocationsRepository eventsLocationsRepository;
    private final EventLocationsMapper eventsLocationMapper;
    private final BookingServiceClient bookingServiceClient;
    private final LocationService locationService;
//    private final KafkaProducer kafkaProducer;

//    @Transactional
//    public List<EventsLocationsDto> addLocationDetails(Event event,
//                                                       List<EventsLocationsData> eventLocationData, Map<Long, Location> locationMap) {
//        validateNoLocationSchedulingConflicts(eventLocationData, locationMap);
//        validateNoArtistSchedulingConflicts(event, eventLocationData);
//
//        List<EventsLocations> eventsLocationsList =
//                eventLocationData.stream().map(eventLoc ->
//                eventsLocationMapper.toEntity(event, eventLoc, locationMap
//                .get(eventLoc.getLocationId()))).toList();
//
//        List<EventsLocations> eventsLocations =
//                eventsLocationsRepository.saveAll(eventsLocationsList);
//
//        List<EventsLocationsDto> mappedEntities =
//                eventsLocations.stream().map(eventsLocationMapper::toDto)
//                .toList();
//
//        return mappedEntities;
//    }

    @Transactional
    public List<EventsLocationsDto> addLocationDetails(Event event,
                                                       List<EventsLocationsData> eventLocationData) {
        log.info("Linking event ID: {} with {} locations", event.getId(),
                eventLocationData.size());

        List<Location> locations =
                locationService.findAllByEventLocationsData(eventLocationData);


        validateNoArtistSchedulingConflicts(event, eventLocationData);

        Map<Long, Location> locationsMap = locations.stream()
                .collect(Collectors.toMap(Location::getId,
                        Function.identity()));

        validateNoLocationSchedulingConflicts(eventLocationData, locationsMap);
        List<EventsLocations> eventsLocations = eventLocationData.stream()
                .map(data -> eventsLocationMapper.toEntity(event, data,
                        locationsMap.get(data.getLocationId())))
                .toList();

        eventsLocationsRepository.saveAll(eventsLocations);
        log.info("Successfully linked event ID: {} with {} locations",
                event.getId(), eventsLocations.size());

        return eventsLocations.stream()
                .map(eventsLocationMapper::toDto)
                .toList();
    }


    public List<EventsLocationsDto> findUpcomingEventLocationsByEventId(long eventId, FetchMode fetchMode) {
        LongFunction<List<EventsLocationsDto>> repositoryCall =
                eventsLocationsRepository::findUpcomingEventLocationsByEventId;

        return fetchMode == FetchMode.WITH_AVAILABILITY
                ? fetchUpcomingEventsWithAvailability("event", eventId,
                repositoryCall)
                : fetchUpcomingEvents("event", eventId, repositoryCall);
    }

    public List<EventsLocationsDto> findAllUpcomingEventsByLocationId(long locationId, FetchMode fetchMode) {
        log.info("Fetching all upcoming events for location ID: {}",
                locationId);

        LongFunction<List<EventsLocationsDto>> repositoryCall =
                eventsLocationsRepository::findAllUpcomingEventsByLocationId;

        return fetchMode == FetchMode.WITH_AVAILABILITY
                ? fetchUpcomingEventsWithAvailability("location", locationId,
                repositoryCall)
                : fetchUpcomingEvents("location", locationId, repositoryCall);
    }

//    public List<EventsLocationsDto> findAllEventsByLocationId(long
//    locationId) {
//        log.info("Fetching all events for specific location ID: {}",
//        locationId);
//        List<EventsLocationsDto> events = fetchUpcomingEvents("Locations",
//        locationId,
//        eventsLocationsRepository::findAllUpcomingEventsByLocationId);
//
//    }


    public List<EventsLocationsDto> findAllUpcomingEventsByArtistId(long artistId, FetchMode fetchMode) {
        log.info("Fetching all upcoming events for artist ID: {}", artistId);

        LongFunction<List<EventsLocationsDto>> repositoryCall =
                eventsLocationsRepository::findAllUpcomingEventsByArtistId;

        return fetchMode == FetchMode.WITH_AVAILABILITY
                ? fetchUpcomingEventsWithAvailability("event", artistId,
                repositoryCall)
                : fetchUpcomingEvents("event", artistId, repositoryCall);
    }

    public List<EventsLocationsDto> findAllByIdsInRange(List<Long> ids,
                                                        FetchMode fetchMode) {
        log.info("Fetching all upcoming events for ids in range {}", ids);

        var eventsLocations = eventsLocationsRepository.findAllInList(ids);

        return eventsLocations;
    }

    public Map<Long, EventsLocationsDto> findAllByIdsAsMap(List<Long> eventsLocationsIds) {
        if (eventsLocationsIds.isEmpty()) {
            return new HashMap<>();
        }
        var eventsLocations =
                eventsLocationsRepository.findAllInList(eventsLocationsIds);

        System.out.println();
        Map<Long, EventsLocationsDto> locationsMap =
                eventsLocations.stream().collect(Collectors.toMap(EventsLocationsDto::getId, eventLocationDto -> eventLocationDto));


        return locationsMap;
    }

//    public void cancelEvents(long eventId, List<EventsLocationsData>
//    cancelEvents) {
//        for (EventsLocationsData location : cancelEvents) {
//            try {
//                LocalDate date = location.getEventDate().toLocalDate();
//                LocalDateTime startOfDay = date.atStartOfDay();
//                LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
//                EventsLocations eventsLocations = eventsLocationsRepository
//                .findByEventLocationIdAndEventIdAndStartDate(location
//                .getLocationId(), startOfDay, endOfDay);
//
//                var eventCancelled = new EventCancelled();
//                eventCancelled.setEventLocationId(eventsLocations.getId());
//                eventsLocations.setEventsLocationsStatus
//                (EventsLocationsStatus.CANCELLED);
//                eventsLocationsRepository.save(eventsLocations);
//                kafkaProducer.sendEventCancellatedMessage(eventCancelled);
//            } catch (Exception e) {
//                log.error("Failed to cancel bookings for event ID: {} at
//                location ID: {}. Error: {}", eventId, location
//                .getLocationId(), e.getMessage());
//            }
//        }
//    }

    public void markAsActive(List<Long> ids) {
        eventsLocationsRepository.updateStatusByIds(ids,
                EventsLocationsStatus.AVAILABLE);
    }

    public EventsLocationsDto findByEventLocationId(long eventId,
                                                    FetchMode fetchMode) {
        log.info("Fetching event location for ID: {}", eventId);

        EventsLocationsDto location =
                eventsLocationsRepository.findEventLocationById(eventId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Event " +
                                        "location not found with id " + eventId));

        if (fetchMode == FetchMode.WITH_AVAILABILITY) {
            enrichWithAvailability(location);
        }

        return location;
    }

    public boolean checkEventLocationStateById(Long eventLocationId) {
        EventsLocationsDto location = eventsLocationsRepository
                .findEventLocationById(eventLocationId).orElseThrow(() -> new ResourceNotFoundException("Evnts"));
        return location.getEventsLocationsStatus() == EventsLocationsStatus.AVAILABLE;
    }

    private List<EventsLocationsDto> fetchUpcomingEvents(String entityType,
                                                         long id,
                                                         LongFunction<List<EventsLocationsDto>> repositoryCall) {
        log.info("Fetching all upcoming events for {} ID: {}", entityType, id);
        return repositoryCall.apply(id);
    }

    private List<EventsLocationsDto> fetchUpcomingEventsWithAvailability(String entityType, long id,
                                                                         LongFunction<List<EventsLocationsDto>> repositoryCall) {
        var events = fetchUpcomingEvents(entityType, id, repositoryCall);
        log.info("Enriching {} events with availability status for {} ID: {}"
                , events.size(), entityType, id);
        events.forEach(this::enrichWithAvailability);
        log.info("Availability enrichment completed for {} ID: {}, total " +
                "events processed: {}", entityType, id, events.size());
        return events;
    }

    private void enrichWithAvailability(EventsLocationsDto eventsLocationsDto) {
        log.debug("Checking availability for event location ID: {}",
                eventsLocationsDto.getId());
        try {
            bookingServiceClient.checkAvailability(eventsLocationsDto.getId()
                    , 1);
            eventsLocationsDto.setEventsLocationsStatus(EventsLocationsStatus.AVAILABLE);
            log.debug("Event location ID: {} is AVAILABLE",
                    eventsLocationsDto.getId());
        } catch (Exception e) {
            log.error("Error checking availability for event location ID {}: " +
                    "{}", eventsLocationsDto.getId(), e.getMessage());
            eventsLocationsDto.setEventsLocationsStatus(EventsLocationsStatus.SOLD_OUT);
        }
    }

    @Transactional
    void validateNoArtistSchedulingConflicts(Event event,
                                             List<EventsLocationsData> eventLocationData) throws LocationCollisionException {
        List<EventsLocationsDto> allUpcomingEventsByArtistId =
                fetchUpcomingEvents("Artists", event.getArtist().getId(),
                        eventsLocationsRepository::findAllUpcomingEventsByArtistId);

        Map<Long, Set<LocalDateTime>> allUpcomingEventsByArtistIdMap =
                allUpcomingEventsByArtistId.stream().collect(Collectors.groupingBy(EventsLocationsDto::getLocationId, Collectors.mapping(EventsLocationsDto::getEventStartTime, Collectors.toSet())));
        Map<Long, Set<LocalDateTime>> eventLocationDataMap =
                eventLocationData.stream().collect(Collectors.groupingBy(EventsLocationsData::getLocationId, Collectors.mapping(EventsLocationsData::getEventStartTime, Collectors.toSet())));

        List<String> collisions = new ArrayList<>();

        for (Map.Entry<Long, Set<LocalDateTime>> entry :
                eventLocationDataMap.entrySet()) {
            Long locationId = entry.getKey();
            Set<LocalDateTime> eventDates = entry.getValue();

            if (allUpcomingEventsByArtistIdMap.containsKey(locationId)) {
                Set<LocalDateTime> existingEventDates =
                        allUpcomingEventsByArtistIdMap.get(locationId);
                for (LocalDateTime eventDate : eventDates) {
                    if (existingEventDates.contains(eventDate)) {
                        collisions.add("Artist already has an event scheduled" +
                                " at location ID: " + locationId + " on date:" +
                                " " + eventDate);
                    }
                }
            }
        }

        if (!collisions.isEmpty()) {
            throw new LocationCollisionException(collisions);
        }
    }

    @Transactional
    void validateNoLocationSchedulingConflicts(List<EventsLocationsData> eventLocationData, Map<Long, Location> locationMap) throws LocationCollisionException {
        List<String> collisions = new ArrayList<>();
        eventLocationData.forEach(x -> {
            LocalDate date = x.getEventStartTime().toLocalDate();
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            if (eventsLocationsRepository.hasEventForLocationInSpecificDate(x.getLocationId(), startOfDay, endOfDay)) {
                collisions.add("Event already exists for location: " + locationMap.get(x.getLocationId()).getName() + " on date: " + x.getEventStartTime());
            }
        });

        if (!collisions.isEmpty()) {
            throw new LocationCollisionException(collisions);
        }
    }

    public LocationDetails findLocationDetails(long id, FetchMode fetchMode) {
        log.info("Fetching location details for ID: {}", id);

        LocationDetails locationDetails =
                locationService.findLocationDetails(id);
        log.debug("Successfully fetched location details for ID: {}, name: {}",
                id, locationDetails.getName());

        LongFunction<List<EventsLocationsDto>> repositoryCall =
                eventsLocationsRepository::findAllUpcomingEventsByLocationId;

        var r = fetchMode == FetchMode.WITH_AVAILABILITY
                ? fetchUpcomingEventsWithAvailability("event", id,
                repositoryCall)
                : fetchUpcomingEvents("event", id, repositoryCall);


        locationDetails.setEventsLocations(r);

        return locationDetails;
    }
}