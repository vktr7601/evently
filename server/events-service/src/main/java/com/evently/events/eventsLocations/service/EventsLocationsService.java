package com.evently.events.eventsLocations.service;


import com.evently.events.eventsLocations.dto.mapper.EventLocationsMapper;
import com.evently.events.event.model.Event;
import com.evently.events.eventsLocations.dto.request.EventsLocationsData;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.model.EventsLocationsStatus;
import com.evently.events.eventsLocations.model.EventsLocations;
import com.evently.events.eventsLocations.repository.EventsLocationsRepository;
import com.evently.events.eventsLocations.service.data.FetchMode;
import com.evently.events.infrastructure.clients.BookingServiceClient;
import com.evently.events.infrastructure.exceptions.LocationCollisionException;
import com.evently.events.locations.model.Location;
import com.evently.events.locations.service.LocationService;
import com.evently.events.locations.dto.LocationDetails;
import events.event.EventLive;
import events.ticket.TicketsCreated;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public List<EventsLocationsDto> addLocationDetails(Event event,
                                                       List<EventsLocationsData> eventLocationData) throws LocationCollisionException {
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

    @Transactional
    public void markAsActive(TicketsCreated ticketsCreated) {
        eventsLocationsRepository.updateStatusByIds(ticketsCreated.getEventLocationIds(),
                EventsLocationsStatus.AVAILABLE);
        
        EventLive eventLive = new EventLive(ticketsCreated.getEventName(),
                ticketsCreated.getCategoryIds());
        eventPublisher.publishEvent(eventLive);
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
    public void validateNoArtistSchedulingConflicts(Event event,
                                                    List<EventsLocationsData> eventLocationData) throws LocationCollisionException {
        // 1. Fetch all upcoming events for this artist (regardless of location)
        List<EventsLocationsDto> allUpcomingEventsByArtist =
                fetchUpcomingEvents("Artists", event.getArtist().getId(),
                        eventsLocationsRepository::findAllUpcomingEventsByArtistId);

        // 2. Create a Set of "Occupied Dates" (LocalDate)
        // This ignores time and just looks at the calendar day
        Set<LocalDate> occupiedDates = allUpcomingEventsByArtist.stream()
                .map(dto -> dto.getEventStartTime().toLocalDate())
                .collect(Collectors.toSet());

        List<String> collisions = new ArrayList<>();

        // 3. Track dates within the CURRENT request to prevent
        // double-booking in one form
        Set<LocalDate> datesInRequest = new HashSet<>();

        for (EventsLocationsData newLoc : eventLocationData) {
            LocalDate requestedDate = newLoc.getEventStartTime().toLocalDate();

            // Check A: Is the artist already booked in the database for this
            // day?
            if (occupiedDates.contains(requestedDate)) {
                collisions.add("Artist already has a performance scheduled " +
                        "on: " + requestedDate);
            }

            // Check B: Are there two entries for the same day in the
            // incoming request?
            if (!datesInRequest.add(requestedDate)) {
                collisions.add("Request contains multiple performances for " +
                        "the same day: " + requestedDate);
            }
        }

        if (!collisions.isEmpty()) {
            throw new LocationCollisionException(collisions);
        }
    }

    @Transactional(readOnly = true)
    public void validateNoArtistSchedulingConflicts(
            Event event,
            List<EventsLocationsData> eventLocationData,
            Set<Long> excludeIds) throws LocationCollisionException {

        // 1. Fetch upcoming events and FILTER OUT the ones we are currently
        // updating
        List<EventsLocationsDto> allUpcomingEventsByArtist =
                fetchUpcomingEvents("Artists", event.getArtist().getId(),
                        eventsLocationsRepository::findAllUpcomingEventsByArtistId);

        // 2. Create a Set of "Occupied Dates", excluding the records we are
        // modifying
        Set<LocalDate> occupiedDates = allUpcomingEventsByArtist.stream()
                .filter(dto -> !excludeIds.contains(dto.getId())) // This
                // prevents self-collision
                .map(dto -> dto.getEventStartTime().toLocalDate())
                .collect(Collectors.toSet());

        List<String> collisions = new ArrayList<>();
        Set<LocalDate> datesInRequest = new HashSet<>();

        for (EventsLocationsData newLoc : eventLocationData) {
            LocalDate requestedDate = newLoc.getEventStartTime().toLocalDate();

            // Check A: Database collision (ignoring current records)
            if (occupiedDates.contains(requestedDate)) {
                collisions.add("Artist already has a performance scheduled " +
                        "on: " + requestedDate);
            }

            // Check B: Intra-request collision (same day twice in the new form)
            if (!datesInRequest.add(requestedDate)) {
                collisions.add("Request contains multiple performances for " +
                        "the same day: " + requestedDate);
            }
        }

        if (!collisions.isEmpty()) {
            throw new LocationCollisionException(collisions);
        }
    }

    @Transactional
    public void validateNoLocationSchedulingConflicts(List<EventsLocationsData> eventLocationData, Map<Long, Location> locationMap) throws LocationCollisionException {
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

    @Transactional
    public void validateNoLocationSchedulingConflicts(List<EventsLocationsData> eventLocationData) throws LocationCollisionException {
        if (eventLocationData.isEmpty()) return;

        // 1. Fetch Location Names for error messages (mapping logic moved here)
        Set<Long> locationIds = eventLocationData.stream()
                .map(EventsLocationsData::getLocationId)
                .collect(Collectors.toSet());

        Map<Long, String> locationNamesMap =
                locationService.findAllByIdIn(locationIds.stream().toList()).stream()
                        .collect(Collectors.toMap(Location::getId,
                                Location::getName));

        List<String> collisions = new ArrayList<>();

        // 2. Perform the checks
        for (EventsLocationsData x : eventLocationData) {
            LocalDate date = x.getEventStartTime().toLocalDate();
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

            // Check against Database
            if (eventsLocationsRepository.hasEventForLocationInSpecificDate(x.getLocationId(), startOfDay, endOfDay)) {
                String locationName =
                        locationNamesMap.getOrDefault(x.getLocationId(),
                                "Unknown Location");
                collisions.add("Event already exists for location: " + locationName + " on date: " + date);
            }
        }

        if (!collisions.isEmpty()) {
            throw new LocationCollisionException(collisions);
        }
    }

    @Transactional(readOnly = true)
    public void validateNoLocationSchedulingConflicts(
            List<EventsLocationsData> eventLocationData,
            Set<Long> excludeIds) throws LocationCollisionException {

        if (eventLocationData.isEmpty()) return;
        Set<Long> locationIds = eventLocationData.stream()
                .map(EventsLocationsData::getLocationId)
                .collect(Collectors.toSet());

        Map<Long, String> locationNamesMap =
                locationService.findAllByIdIn(new ArrayList<>(locationIds)).stream()
                        .collect(Collectors.toMap(Location::getId,
                                Location::getName));
        // 1. Fetch Location Names for descriptive error messages

        List<String> collisions = new ArrayList<>();

        // 2. Perform the checks
        for (EventsLocationsData x : eventLocationData) {
            LocalDate date = x.getEventStartTime().toLocalDate();
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

            // Update the repository call to include the exclusion list

            Optional<EventsLocations> existingEvent = eventsLocationsRepository
                    .findEventByLocationAndDate(x.getLocationId(), startOfDay
                            , endOfDay);

            // 2. Logic: If an event exists AND it's not the one we are
            // currently updating
            if (existingEvent.isPresent()) {
                Long existingId = existingEvent.get().getId();

                if (!excludeIds.contains(existingId)) {
                    // This is a REAL collision with a different record
                    String locationName =
                            locationNamesMap.getOrDefault(x.getLocationId(),
                                    "Unknown Location");
                    collisions.add("Location '" + locationName + "' is " +
                            "already booked on: " + date);
                }
            }
        }

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