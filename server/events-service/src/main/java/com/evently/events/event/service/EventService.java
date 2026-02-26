package com.evently.events.event.service;


import com.evently.events.artists.model.Artist;
import com.evently.events.artists.service.ArtistsService;
import com.evently.events.category.dto.CategoryDto;
import com.evently.events.event.dto.EventDetailDto;
import com.evently.events.event.dto.EventListItemDto;
import com.evently.events.event.dto.mapper.EventMapper;
import com.evently.events.event.dto.request.EventCreateRequest;
import com.evently.events.event.dto.request.EventUpdateRequest;
import com.evently.events.event.model.Event;
import com.evently.events.event.repository.EventRepository;
import com.evently.events.eventsCategories.dto.EventCategoriesDto;
import com.evently.events.eventsCategories.service.EventsCategoriesService;
import com.evently.events.eventsLocations.dto.request.EventsLocationsData;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.model.EventsLocations;
import com.evently.events.eventsLocations.model.EventsLocationsStatus;
import com.evently.events.eventsLocations.repository.EventsLocationsRepository;
import com.evently.events.eventsLocations.service.EventsLocationsService;
import com.evently.events.eventsLocations.service.data.FetchMode;
import com.evently.events.infrastructure.clients.BookingServiceClient;
import events.event.*;
import events.ticket.TicketsCreationEvent;
import events.user.UserRegisteredEvent;
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
    public EventDetailDto createEvent(EventCreateRequest eventRequestDto) throws DuplicateResourceException {
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
                categories.stream().map(CategoryDto::getId).toList(),
                event.getArtist().getId(),
                event.getName(),
                tickets
        );

        eventPublisher.publishEvent(eventCreated);

        return eventsMapper.toDetailDto(event, eventLocations, categories);
    }

    @Transactional
    @CachePut(cacheNames = "events.eventDetails", key = "#result.id")
    public EventDetailDto updateEvent(Long id,
                                      EventUpdateRequest updateRequest) {
        // 1. Fetch current state
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not " +
                        "found"));


        Map<Long, EventsLocationsDto> existingLocsMap = eventsLocationsService
                .findUpcomingEventLocationsByEventId(id, FetchMode.BASIC)
                .stream()
                .collect(Collectors.toMap(EventsLocationsDto::getId,
                        Function.identity()));
        Set<Long> incomingIds = updateRequest.getEventLocations().stream()
                .map(EventsLocationsData::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // --- BUCKET 2: NEW (In Request, but no ID) ---
        List<EventsLocationsData> newLocData =
                updateRequest.getEventLocations().stream()
                        .filter(loc -> loc.getId() == null)
                        .toList();

        if (!newLocData.isEmpty()) {
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
            Set<Long> excludeIds = actualUpdates.stream()
                    .map(EventsLocationsData::getId)
                    .collect(Collectors.toSet());
            eventsLocationsService.validateNoArtistSchedulingConflicts(event,
                    actualUpdates, excludeIds);

            eventsLocationsService.validateNoLocationSchedulingConflicts(actualUpdates, excludeIds);
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
            eventTicketsUpdate.setEventLocationId(req.getId());
            if (entity == null) continue;

            if (!entity.getDate().equals(req.getEventStartTime())) {
                eventTicketsUpdate.setNewStartTime(req.getEventStartTime());
                eventTicketsUpdate.setDateUpdated(true);
                entity.setDate(req.getEventStartTime());
            }

            if (entity.getTotalTickets() < req.getTickets()) {
                eventTicketsUpdate.setTicketCountUpdated(true);
                eventTicketsUpdate.setNewTicketsCount(req.getTickets());
                entity.setTotalTickets(req.getTickets());
            }

            if (!Objects.equals(entity.getPrice(), req.getPrice())) {
                eventTicketsUpdate.setNewPrice(req.getPrice());
                eventTicketsUpdate.setPriceUpdated(true);
                entity.setPrice(req.getPrice());
            }

            eventTicketsBulkUpdate.getUpdates().add(eventTicketsUpdate);
        }
        eventsLocationsRepository.saveAll(existingMap.values());

        eventPublisher.publishEvent(eventTicketsBulkUpdate);
    }

    @Transactional
    public void generateUserFeed(UserRegisteredEvent userRegisteredEvent) {
        boolean hasCategories =
                !userRegisteredEvent.getFollowCategories().isEmpty();
        boolean hasLocations =
                !userRegisteredEvent.getFollowLocations().isEmpty();

    }

    public void generateUserFeed(){

    }

    private void processAdditions(Event event,
                                  List<EventsLocationsData> data) throws DuplicateResourceException {
        List<EventsLocationsDto> created =
                eventsLocationsService.addLocationDetails(event, data);

        List<TicketsCreationEvent> tickets = created.stream()
                .map(this::toTicketsCreationEvent).toList();

        eventPublisher.publishEvent(new EventUpdated(event.getId(),
                tickets));
    }

    @Transactional
    public int clearHistoryEvents() {
        List<Long> idsToProcess =
                eventsLocationsRepository.findIdsByStatusAndDate(LocalDateTime.now(), EventsLocationsStatus.AVAILABLE);

        if (!idsToProcess.isEmpty()) {
            eventsLocationsRepository.updateStatusByIds(idsToProcess,
                    EventsLocationsStatus.COMPLETED);

            eventPublisher.publishEvent(new EventArchived(idsToProcess));
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