package com.evently.events.event;


import com.evently.events.artists.Artist;
import com.evently.events.artists.ArtistsService;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.event.entities.*;
import com.evently.events.eventsCategories.EventsCategoriesService;
import com.evently.events.eventsCategories.entities.EventCategoriesDto;
import com.evently.events.eventsLocations.BookingServiceClient;
import com.evently.events.eventsLocations.EventsLocations;
import com.evently.events.eventsLocations.EventsLocationsRepository;
import com.evently.events.eventsLocations.EventsLocationsService;
import com.evently.events.eventsLocations.entities.EventsLocationsData;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.entities.EventsLocationsStatus;
import com.evently.events.eventsLocations.entities.FetchMode;
import dtos.EventFinished;
import events.eventCreated.*;
import exceptions.DuplicateResourceException;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventsCategoriesService eventsCategoriesService;
    private final EventsLocationsService eventsLocationsService;
    private final EventRepository eventRepository;
    private final ArtistsService artistsService;
    private final EventMapper eventsMapper;
    private final BookingServiceClient bookingServiceClient;
    private final EventsLocationsRepository eventsLocationsRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<EventListItemDto> findAllEventsByCategoryName(String categoryName) {
        List<EventCategoriesDto> allEventsByCategoryId =
                eventsCategoriesService.getEventsByCategoryName(categoryName);

        List<Long> eventsIds =
                allEventsByCategoryId.stream().map(EventCategoriesDto::getEventId).toList();

        List<EventListItemDto> eventsListItems =
                eventRepository.findAllByEventsIdsIn(eventsIds);

        eventsCategoriesService.addCategoriesToEventListItems(eventsListItems);

        return eventsListItems;
    }

    // @Cacheable(cacheNames = "events.list", key =
    // "'allEventsSortedByDateDesc'")
    public List<EventListItemDto> findAllEventsSortedByDateDesc() {
        log.info("Starting process to fetch all events sorted by date.");

        List<EventListItemDto> events =
                eventRepository.findAllEventsSortedByDateDesc();

        log.info("Fetching categories for {} events in bulk.", events.size());

        eventsCategoriesService.addCategoriesToEventListItems(events);

        log.info("Successfully processed and enriched {} events with " +
                "categories.", events.size());
        return events;
    }

    // @Cacheable(cacheNames = "events.eventDetails", key = "#id")
    public EventDetailDto findEventDetailsById(Long id) {
        log.info("Attempting to find details for event ID: {}", id);

        EventDetailDto event =
                eventRepository.findEventDetailsById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        log.debug("Event found: {}. Fetching additional data (categories and " +
                "venues).", event.getEventName());

        List<CategoryDto> eventCategories =
                eventsCategoriesService.getEventCategories(event.getId());
        event.setCategoryDtoList(eventCategories);
        log.info("Fetched {} categories for event ID: {}",
                eventCategories.size(), id);

        List<EventsLocationsDto> locationsByEventId =
                eventsLocationsService.findUpcomingEventLocationsByEventId(event.getId(), FetchMode.WITH_AVAILABILITY);
        event.setEventsLocations(locationsByEventId);
        log.info("Fetched {} upcoming locations/venues for event ID: {}",
                locationsByEventId.size(), id);

        log.info("Successfully assembled full details for event: {}",
                event.getEventName());
        return event;
    }

    @Transactional
    public EventDetailDto createEvent(EventCreate eventRequestDto) throws DuplicateResourceException {
        if (eventRepository.existsByName(eventRequestDto.getName()))
            throw new DuplicateResourceException(("Event with name: %s " +
                    "already" +
                    " exists").formatted(eventRequestDto.getName()));

        Artist artist = artistsService.findById(eventRequestDto.getArtistId());

        Event event = eventsMapper.toEntity(eventRequestDto, artist);

        eventRepository.save(event);

        List<CategoryDto> categories =
                eventsCategoriesService.categorizeEvent(event,
                        eventRequestDto.getCategories());

        List<EventsLocationsDto> eventLocations =
                eventsLocationsService.addLocationDetails(event,
                        eventRequestDto.getEventLocations());

        List<TicketsCreationEvent> tickets = eventLocations.stream()
                .map(this::toTicketsCreationEvent)
                .toList();

        EventCreated eventCreated = new EventCreated(
                event.getId(),
                event.getName(),
                tickets
        );

        eventPublisher.publishEvent(eventCreated);

        return eventsMapper.toDetailDto(event, eventLocations, categories);
    }

    @Transactional
    @CachePut(cacheNames = "events.eventDetails", key = "#result.id")
    public EventDetailDto updateEvent(Long id, EventUpdate updateRequest) {
        // 1. Fetch current state
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not " +
                        "found"));


// Map existing locations by ID for O(1) lookups
        Map<Long, EventsLocationsDto> existingLocsMap = eventsLocationsService
                .findUpcomingEventLocationsByEventId(id, FetchMode.BASIC)
                .stream()
                .collect(Collectors.toMap(EventsLocationsDto::getId,
                        Function.identity()));
        // 2. Identify the Deltas
        Set<Long> incomingIds = updateRequest.getEventLocations().stream()
                .map(EventsLocationsData::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // --- BUCKET 1: DELETED (In DB, but not in Request) ---
//        List<Long> deletedIds = existingLocs.stream()
//                .map(EventsLocationsDto::getId)
//                .filter(dbId -> !incomingIds.contains(dbId))
//                .toList();

//        if (!deletedIds.isEmpty()) {
//            processDeletions(deletedIds);
//        }

        // --- BUCKET 2: NEW (In Request, but no ID) ---
        List<EventsLocationsData> newLocData =
                updateRequest.getEventLocations().stream()
                        .filter(loc -> loc.getId() == null)
                        .toList();

        if (!newLocData.isEmpty()) {
            //      eventsLocationsService
            //      .validateNoArtistSchedulingConflicts(event,
            //                    updates);
            //            eventsLocationsService
            //            .validateNoArtistSchedulingConflicts(event,
            //                    updates);
            processAdditions(event, newLocData);
        }

        // --- BUCKET 3: UPDATED (In Request AND in DB) ---
        List<EventsLocationsData> actualUpdates =
                updateRequest.getEventLocations().stream()
                        .filter(loc -> loc.getId() != null)
                        .filter(req -> {
                            EventsLocationsDto existing =
                                    existingLocsMap.get(req.getId());
                            if (existing == null) return false;

                            // Check if anything actually changed
                            boolean dateChanged =
                                    !existing.getEventStartTime().equals(req.getEventStartTime());
                            boolean priceChanged =
                                    existing.getPricePerTicket().compareTo(req.getPrice()) != 0;
                            boolean countChanged =
                                    existing.getTicketsCount() != req.getTickets();

                            return dateChanged || priceChanged || countChanged;
                        })
                        .toList();

        if (!actualUpdates.isEmpty()) {
            eventsLocationsService.validateNoArtistSchedulingConflicts(event,
                    actualUpdates);

            eventsLocationsService.validateNoLocationSchedulingConflicts(actualUpdates);
            processUpdates(actualUpdates);
        }

        eventRepository.save(event);

        return findEventDetailsById(event.getId());
    }

    private void processDeletions(List<Long> deletedIds) {

    }

    public EventsLocationsDto getEventLocationData(Long eventId) {
        return eventsLocationsService.findByEventLocationId(eventId,
                FetchMode.WITH_AVAILABILITY);
    }

    @Transactional
    public void processUpdates(List<EventsLocationsData> updates) {
        Map<Long, EventsLocations> existingMap =
                eventsLocationsRepository.findAllById(
                        updates.stream().map(EventsLocationsData::getId).toList()
                ).stream().collect(Collectors.toMap(EventsLocations::getId,
                        loc -> loc));
        EventTicketsBulkUpdate eventTicketsBulkUpdate =
                new EventTicketsBulkUpdate();
        for (EventsLocationsData req : updates) {
            EventsLocations entity = existingMap.get(req.getId());
            EventTicketsUpdate eventTicketsUpdate = new EventTicketsUpdate();
            if (entity == null) continue;

            if (!entity.getDate().equals(req.getEventStartTime())) {
                eventTicketsUpdate.setNewStartTime(req.getEventStartTime());
                eventTicketsUpdate.setDateUpdated(true);
                entity.setDate(req.getEventStartTime());
            }

            eventTicketsBulkUpdate.getUpdates().add(eventTicketsUpdate);
        }
        eventsLocationsRepository.saveAll(existingMap.values());

        eventPublisher.publishEvent(eventTicketsBulkUpdate);
    }

    private void processAdditions(Event event,
                                  List<EventsLocationsData> data) throws DuplicateResourceException {
        List<EventsLocationsDto> created =
                eventsLocationsService.addLocationDetails(event, data);

        List<TicketsCreationEvent> tickets = created.stream()
                .map(this::toTicketsCreationEvent).toList();

        eventPublisher.publishEvent(new NewLocationsAdded(event.getId(),
                tickets));
    }

    @Transactional
    public int clearHistoryEvents() {
        List<Long> idsToProcess =
                eventsLocationsRepository.findIdsByStatusAndDate(LocalDateTime.now(), EventsLocationsStatus.AVAILABLE);

        if (!idsToProcess.isEmpty()) {
            eventsLocationsRepository.updateStatusByIds(idsToProcess,
                    EventsLocationsStatus.COMPLETED);

            eventPublisher.publishEvent(new EventFinished(idsToProcess));
        }

        return idsToProcess.size();
    }

    private TicketsCreationEvent toTicketsCreationEvent(EventsLocationsDto location) {
        return new TicketsCreationEvent(
                location.getId(),
                location.getTicketsCount(),
                location.getEventStartTime(),
                location.getPricePerTicket()
        );
    }
}