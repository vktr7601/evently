package com.evently.events.event;


import com.evently.events.category.entities.CategoryDto;
import com.evently.events.category.entities.CategoryMapper;
import com.evently.events.config.KafkaProducer;
import com.evently.events.event.entities.EventDetailsDto;
import com.evently.events.event.entities.EventListDto;
import com.evently.events.event.entities.EventMapper;
import com.evently.events.eventsCategories.EventsCategoriesService;
import com.evently.events.eventsLocations.EventsLocationsService;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.artists.ArtistsRepository;
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
    private final EventMapper eventMapper;
    private final EventsLocationsService eventsVenuesService;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;
    private final ArtistsRepository artistsRepository;
    //   private final TicketClient ticketClient;
    private final KafkaProducer kafkaProducer;


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

    public List<EventListDto> findAllEventsSortedByDateDesc() {
        log.info("Starting process to fetch all events sorted by date.");

        var events = eventRepository.findAllEventsSortedByDateDesc();
        log.debug("Found {} events in database.", events.size());

        if (events.isEmpty()) {
            log.info("No events found in database.");
            return List.of();
        }

        List<Long> eventsIds = events.stream().map(EventListDto::getId).toList();

        log.info("Fetching categories for {} events in bulk.", eventsIds.size());
        Map<Long, List<CategoryDto>> eventCategoryMap = eventsCategoriesService.findAllCategoriesByEventIds(eventsIds);

        events.forEach(e -> {
            List<CategoryDto> categories = eventCategoryMap.get(e.getId());
            e.setCategoryDtoList(categories != null ? categories : List.of());
        });

        log.info("Successfully processed and enriched {} events with categories.", events.size());
        return events;
    }

    public EventDetailsDto findEventDetailsById(Long id) {
        log.info("Attempting to find details for event ID: {}", id);

        EventDetailsDto event = eventRepository.findEventDtoById(id).orElseThrow(() -> {
            log.info("ResourceNotFoundException: Event with ID {} not found", id);
            return new ResourceNotFoundException("Event not found with id: " + id);
        });

        log.debug("Event found: {}. Fetching additional data (categories and venues).", event.getName());

        List<CategoryDto> eventCategories = eventsCategoriesService.getEventCategories(event.getId());
        event.setCategoryDtoList(eventCategories);
        log.info("Fetched {} categories for event ID: {}", eventCategories.size(), id);

        List<EventsLocationsDto> locationsByEventId = eventsVenuesService.findUpcomingEventLocationsByEventId(event.getId());
        event.setEventLocationData(locationsByEventId);
        log.info("Fetched {} upcoming locations/venues for event ID: {}", locationsByEventId.size(), id);

        log.info("Successfully assembled full details for event: {}", event.getName());
        return event;
    }
}