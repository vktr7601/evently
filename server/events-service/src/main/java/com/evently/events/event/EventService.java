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
import com.evently.events.locations.Location;
import com.evently.events.locations.LocationService;
import dtos.EventCreated;
import dtos.TicketAllocation;
import exceptions.DuplicateResourceException;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventsCategoriesService eventsCategoriesService;
    private final EventsLocationsService eventsLocationsService;
    private final EventRepository eventRepository;
    private final ArtistsService artistsService;
    private final EventMapper eventMapper;
    private final LocationService locationService;
    private final KafkaProducer kafkaProducer;
    private final EventsLocationsRepository eventsLocationsRepository;

    public List<EventListItemDto> findAllEventsByCategoryName(String categoryName) {
        List<EventCategoriesDto> allEventsByCategoryId = eventsCategoriesService.getEventsByCategoryName(categoryName);

        List<Long> eventsIds = allEventsByCategoryId.stream().map(EventCategoriesDto::getEventId).toList();

        List<EventListItemDto> eventsListItems = eventRepository.findAllByEventsIdsIn(eventsIds);

        eventsCategoriesService.addCategoriesToEventListItems(eventsListItems);

        return eventsListItems;
    }

    @Cacheable(cacheNames = "events", key = "'allEventsSortedByDateDesc'")
    public List<EventListItemDto> findAllEventsSortedByDateDesc() {
        log.info("Starting process to fetch all events sorted by date.");

        List<EventListItemDto> events = eventRepository.findAllEventsSortedByDateDesc();

        log.info("Fetching categories for {} events in bulk.", events.size());

        eventsCategoriesService.addCategoriesToEventListItems(events);

        log.info("Successfully processed and enriched {} events with categories.", events.size());
        return events;
    }

    @Cacheable(cacheNames = "eventDetails", key = "#id")
    public EventDetailDto findEventDetailsById(Long id) {
        log.info("Attempting to find details for event ID: {}", id);

        EventDetailDto event = eventRepository.findEventDetailsById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        log.debug("Event found: {}. Fetching additional data (categories and venues).", event.getName());

        List<CategoryDto> eventCategories = eventsCategoriesService.getEventCategories(event.getId());
        event.setCategoryDtoList(eventCategories);
        log.info("Fetched {} categories for event ID: {}", eventCategories.size(), id);

        List<EventsLocationsDto> locationsByEventId = eventsLocationsService.findUpcomingEventLocationsByEventId(event.getId());
        event.setEventLocationData(locationsByEventId);
        log.info("Fetched {} upcoming locations/venues for event ID: {}", locationsByEventId.size(), id);

        log.info("Successfully assembled full details for event: {}", event.getName());
        return event;
    }

    @Transactional
    @CachePut(cacheNames = "eventDetails", key = "#result.id")
    @CacheEvict(cacheNames = "events", key = "'allEventsSortedByDateDesc'")
    public EventDetailDto createEvent(CreateEventRequest eventRequestDto) throws DuplicateResourceException {
        if (eventRepository.existsByName(eventRequestDto.getName())) {
            throw new DuplicateResourceException("Event with name: %s already exists".formatted(eventRequestDto.getName()));
        }
        Event event = eventMapper.toEntity(eventRequestDto);
        Artist artist = artistsService.findById(eventRequestDto.getArtistId());
        event.setArtist(artist);
        event.setImageUrl(artist.getImageUrl());
        eventRepository.save(event);
        List<CategoryDto> assignedDto = eventsCategoriesService.categorize(event, eventRequestDto.getCategories());
        List<Long> list = eventRequestDto.eventLocations.stream().map(EventsLocationsData::getLocationId).toList();
        Map<Long, Location> allByIdIn = locationService.findAllByIdIn(list);

        List<EventsLocationsDto> eventsLocations = eventsLocationsService.addLocationDetails(event, eventRequestDto.getEventLocations(), allByIdIn);


        EventDetailDto eventDetailDto = eventMapper.toDetailDto(event, eventsLocations, assignedDto);


        List<TicketAllocation> ticketAllocations = eventsLocations.stream().map(x -> new TicketAllocation(x.getEventLocationId(), x.getTicketsCount(), x.getEventStartTime(), x.getPricePerTicket())).toList();
        var eventCreated = new EventCreated();
        eventCreated.setEventName(event.getName());
        eventCreated.setEventId(event.getId());
        eventCreated.setCategories(assignedDto.stream().map(CategoryDto::id).toList());
        eventCreated.setPerformer(artist.getId());
        eventCreated.setTicketAllocations(ticketAllocations);
        kafkaProducer.sendEventCreatedMessage(eventCreated);
        return eventDetailDto;
    }


    @Transactional
    @CachePut(cacheNames = "eventDetails", key = "#result.id")
    public void updateEvent(UpdateEventRequest updateEventRequest) {
        Event event = eventRepository.findById(updateEventRequest.getEventId()).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + updateEventRequest.getEventId()));
        if (updateEventRequest.getEventName() != null) {
            event.setName(updateEventRequest.getEventName());
        }
        if (updateEventRequest.getDescription() != null) {
            event.setDescription(updateEventRequest.getDescription());
        }
        eventRepository.save(event);

    }

    public EventsLocationsDto getEventLocationData(Long eventId) {
        var eventsLocations = eventsLocationsRepository.findByEventLocationId(eventId);
        return eventsLocations;
    }
}