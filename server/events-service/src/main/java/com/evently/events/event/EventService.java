package com.evently.events.event;


import com.evently.events.category.entities.CategoryDto;
import com.evently.events.category.entities.CategoryMapper;
import com.evently.events.event.entities.EventDto;
import com.evently.events.event.entities.EventMapper;
import com.evently.events.event.entities.EventRequestDto;
import com.evently.events.event.entities.EventResponseDto;
import com.evently.events.eventsCategories.EventsCategoriesRepository;
import com.evently.events.eventsCategories.EventsCategoriesService;
import com.evently.events.eventsVenues.EventsVenuesService;
import com.evently.events.eventsVenues.entities.EventsVenuesDto;
import com.evently.events.performers.Performer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    //  @CachePut(value = "events", key = "'id:' + #result.getId()")
    public EventResponseDto create(EventRequestDto request) {
        Event event = eventMapper.toEntity(request);
        eventRepository.save(event);
        eventsCategoriesService.categorize(event, request.getCategories());
        List<EventsVenuesDto> list = eventsVenuesService.create(event, request.getEventLocationData());

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