package com.evently.events.event;


import com.evently.events.category.entities.CategoryDto;
import com.evently.events.category.entities.CategoryMapper;
import com.evently.events.config.KafkaProducer;
import com.evently.events.event.entities.EventDto;
import com.evently.events.event.entities.EventMapper;
import com.evently.events.event.entities.EventRequestDto;
import com.evently.events.event.entities.EventResponseDto;
import com.evently.events.eventsCategories.EventsCategoriesRepository;
import com.evently.events.eventsCategories.EventsCategoriesService;
import com.evently.events.eventsVenues.EventsVenuesService;
import com.evently.events.eventsVenues.entities.EventsVenuesDto;
import com.evently.events.performers.Performer;
import com.evently.events.performers.PerformerRepository;
import dtos.CreateTicketsDto;
import dtos.EventCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventsCategoriesService eventsCategoriesService;
    private final EventsCategoriesRepository eventsCategoriesRepository;
    private final EventMapper eventMapper;
    private final EventsVenuesService eventsVenuesService;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;
    private final PerformerRepository performerRepository;
    //   private final TicketClient ticketClient;
    private final KafkaProducer kafkaProducer;
    private final KafkaTemplate<String, EventCreated> kafkaTemplate;


    @Transactional
    //  @CachePut(value = "events", key = "'id:' + #result.getId()")
    public EventResponseDto create(EventRequestDto request) {
        Event event = eventMapper.toEntity(request);
        Performer performer = performerRepository.findByName(request.getPerformer());
        event.setPerformer(performer);
        eventRepository.save(event);
        var res = eventsCategoriesService.categorize(event, request.getCategories());
        List<EventsVenuesDto> list = eventsVenuesService.create(event, request.getEventLocationData());
        List<CreateTicketsDto> list1 = list.stream().map(x -> new CreateTicketsDto(x.venueId(), x.totalTickets(), x.date())).toList();
        // ticketClient.createTickets(list1);
        List<Long> categoriesIds = res.stream().map(BaseEntity::getId).toList();
        EventCreated eventCreated = EventCreated.of(event.getId(), categoriesIds, performer.getId(), event.getName());
        kafkaProducer.sendEventCreatedMessage( eventCreated);


        return eventMapper.toResponseDto(event, request.getCategories(), list);
    }

    public List<EventDto> findAll() {
        var events = eventRepository.findAll();
        List<EventDto> eventDtos = new ArrayList<>();
        for (var event : events) {
            Performer performer = event.getPerformer();
            List<CategoryDto> categories = eventsCategoriesRepository.findCategoriesByEventId(event.getId()).stream().map(categoryMapper::toDto).toList();
            eventDtos.add(new EventDto(event.getName(), categories, performer.name, event.getId()));
        }

        return eventDtos;
    }
    // @Cacheable(value = "events", key = "'id:' + #id")
//    public EventResponseDto getById(long id) {
//        Event event = eventRepository.findByIdOrThrow(id);
//        List<EventsVenuesDto> locationsBy = eventsVenuesService.getLocationsBy(id);
//        List<String> cat = eventsClassificationsService.getEventCategories(id);
//        return EventResponseDto.of(event, cat, locationsBy);
//    }
}