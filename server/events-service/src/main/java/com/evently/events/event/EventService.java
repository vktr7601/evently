package com.evently.events.event;


import com.evently.events.artists.Artist;
import com.evently.events.artists.ArtistsRepository;
import com.evently.events.artists.ArtistsService;
import com.evently.events.category.CategoryService;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.event.entities.*;
import com.evently.events.eventsCategories.EventsCategoriesService;
import com.evently.events.eventsCategories.entities.EventCategoriesDto;
import com.evently.events.eventsLocations.EventsLocationsService;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.locations.Location;
import com.evently.events.locations.LocationService;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ArtistsRepository artistsRepository;
    private final CategoryService categoryService;
    private final EventMapper eventMapper;
    private final LocationService locationService;


    @Transactional
    //  @CachePut(value = "events", key = "'id:' + #result.getId()")
//    public EventResponseDto create(EventRequestDto request) {
//        Event event = eventMapper.toEntity(request);
//        Artist artist = artistsRepository.findByName(request.getArtist());
//        event.setArtist(artist);
//        eventRepository.save(event);
//        var res = eventsCategoriesService.categorize(event, request.getCategories());
//        List<EventsVenuesDto> list = eventsVenuesService.create(event, request.getEventLocationData());
//        List<CreateTicketsDto> list1 = list.stream().map(x -> new CreateTicketsDto(x.venueId(), x.totalTickets(), x.date())).toList();
//        // ticketClient.createTickets(list1);
//        List<Long> categoriesIds = res.stream().map(BaseEntity::getId).toList();
//        EventCreated eventCreated = EventCreated.of(event.getId(), categoriesIds, artist.getId(), event.getName());
//        kafkaProducer.sendEventCreatedMessage(eventCreated);
//
//
//        return eventMapper.toResponseDto(event, request.getCategories(), list);
//    }

    public List<EventListItemDto> findAllEventsByCategoryName(String categoryName) {
        List<EventCategoriesDto> allEventsByCategoryId = eventsCategoriesService.getEventsByCategoryName(categoryName);

        List<Long> eventsIds = allEventsByCategoryId.stream().map(EventCategoriesDto::getEventId).toList();

        List<EventListItemDto> eventsListItems = eventRepository.findAllByEventsIdsIn(eventsIds);

        eventsCategoriesService.addCategoriesToEventListItems(eventsListItems);

        return eventsListItems;
    }

    @Transactional
    public List<EventListItemDto> findAllEventsSortedByDateDesc() {
        log.info("Starting process to fetch all events sorted by date.");

        List<EventListItemDto> events = eventRepository.findAllEventsSortedByDateDesc();

        log.info("Fetching categories for {} events in bulk.", events.size());

        eventsCategoriesService.addCategoriesToEventListItems(events);

        log.info("Successfully processed and enriched {} events with categories.", events.size());
        return events;
    }

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
    public EventDetailDto createEvent(EventRequestDto eventRequestDto) {
        Event event = eventMapper.toEntity(eventRequestDto);
        Artist artist = artistsService.findById(eventRequestDto.getArtistId());
        event.setArtist(artist);
        eventRepository.save(event);
        List<CategoryDto> assignedDto = eventsCategoriesService.categorize(event, eventRequestDto.getCategories());
        Map<Long, Location> map = locationService.findAllByIdIn(eventRequestDto.eventLocations.stream().map(EventLocationData::getLocationId).toList());
        List<EventsLocationsDto> eventsLocations = eventsLocationsService.addLocationDetails(event, eventRequestDto.getEventLocations(), map);


        EventDetailDto eventDetailDto = eventMapper.toDetailDto(event,  eventsLocations, assignedDto);
    //invoke kafka event created
        return eventDetailDto;
    }
}