package com.evently.events.event;


import com.evently.events.artists.Artist;
import com.evently.events.artists.ArtistsService;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.config.KafkaProducer;
import com.evently.events.event.entities.*;
import com.evently.events.eventsCategories.EventsCategoriesService;
import com.evently.events.eventsCategories.entities.EventCategoriesDto;
import com.evently.events.eventsLocations.EventsLocationsRepository;
import com.evently.events.eventsLocations.EventsLocationsService;
import com.evently.events.eventsLocations.entities.EventsLocationsData;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.entities.EventsLocationsStatus;
import com.evently.events.eventsLocations.entities.FetchMode;
import com.evently.events.locations.LocationService;
import dtos.EventFinished;
import events.eventCreated.EventCreated;
import events.eventCreated.TicketsCreationEvent;
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
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventsCategoriesService eventsCategoriesService;
    private final EventsLocationsService eventsLocationsService;
    private final EventRepository eventRepository;
    private final ArtistsService artistsService;
    //  private final EventMapper eventMapper;
    private final EventsMapper eventsMapper;
    private final LocationService locationService;
    private final KafkaProducer kafkaProducer;
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

    //  @CachePut(cacheNames = "events.eventDetails", key = "#result.id")
    // @CacheEvict(cacheNames = "events.list", key =
    //   "'allEventsSortedByDateDesc'")

    @Transactional
    public EventDetailDto createEvent(CreateEventRequest eventRequestDto) throws DuplicateResourceException {
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
                artist.getId(),
                event.getName(),
                tickets
        );

        eventPublisher.publishEvent(eventCreated);

        return eventsMapper.toDetailDto(event, eventLocations, categories);
    }


    @Transactional
    @CachePut(cacheNames = "events.eventDetails", key = "#result.id")
    public EventDetailDto updateEvent(Long id,
                                      UpdateEventRequest updateEventRequest) {

//        updateEventRequest.getEventLocations().forEach(loc -> {
//            if (loc.getEventDate().isBefore(LocalDateTime.now().plusDays(2)
//            )) {
//                throw new IllegalArgumentException("Location at ID " + loc
//                .getLocationId() + " must be at least 2 days in the future.");
//            }
//        });

        Event event =
                eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        List<EventsLocationsDto> existingLocations =
                eventsLocationsService.findUpcomingEventLocationsByEventId(id
                        , FetchMode.BASIC);

        Set<Long> idsInUserForm =
                updateEventRequest.getEventLocations().stream().map(EventsLocationsData::getEventLocationId).collect(Collectors.toSet());

        List<EventsLocationsDto> deletedLocations =
                existingLocations.stream().filter(loc -> !idsInUserForm.contains(loc.getId())).toList();

        Set<Long> idsInDatabase =
                existingLocations.stream().map(EventsLocationsDto::getId).collect(Collectors.toSet());

        List<EventsLocationsData> newLocations =
                updateEventRequest.getEventLocations().stream().filter(loc -> !idsInDatabase.contains(loc.getEventLocationId())).toList();

        //this is handling the logic when new event location is added
        //   if (!newLocations.isEmpty()) {
//            List<Long> newLocationIds =
//                    newLocations.stream().map
//                    (EventsLocationsData::getLocationId).toList();
//            Map<Long, Location> locationEntitiesMap =
//                    locationService.findAllByIdIn(newLocationIds);
//            List<CategoryDto> categories =
//                    eventsCategoriesService.getEventCategories(event.getId());
//
//            // 2. Add them and get back the DTOs (which likely contain the
//            // new DB primary keys)
////            List<EventsLocationsDto> addedDtos =
////                    eventsLocationsService.addLocationDetails(event,
////                            newLocations, locationEntitiesMap);
//            eventsLocationsService.addLocationDetails(event,
//                    updateEventRequest.getEventLocations());
//            // 3. Prepare Kafka Message (Ticket Allocations)
//            List<TicketsCreationEvent> ticketsCreationEvents =
//                    addedDtos.stream().map(x -> new TicketsCreationEvent(x
//                    .getId(), x.getTicketsCount(), x.getEventStartTime(), x
//                    .getPricePerTicket())).toList();
////            var eventCreated = new EventCreated();
//            eventCreated.setEventName(event.getName());
//            eventCreated.setEventId(event.getId());
//            eventCreated.setCategories(categories.stream().map
//            (CategoryDto::id).toList());
//            eventCreated.setArtistId(event.getArtist().getId());
//            eventCreated.setTicketsCreationEvents(ticketsCreationEvents);
        // eventPublisher.publishEvent(eventCreated);
//        }
//        if (!idsInDatabase.isEmpty()) {
//            // this is handling the logic when event location is updated,
//            // meaning that user changed the date or price for existing
//            location
//        }
//
//        if (!deletedLocations.isEmpty()) {
//            List<Long> deletedLocationIds =
//                    deletedLocations.stream().map
//                    (EventsLocationsDto::getId).toList();
//
//            //handle the logic for deleting
//        }
//        //if there is removed locations, i need to
//
//        eventRepository.save(event);
//
//
//        return findEventDetailsById(id);
        return null;
    }

    //this is invoked by the frontend
    public EventsLocationsDto getEventLocationData(Long eventId) {

        return eventsLocationsService.findByEventLocationId(eventId,
                FetchMode.WITH_AVAILABILITY);
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