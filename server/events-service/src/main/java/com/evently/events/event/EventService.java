package com.evently.events.event;


import com.evently.events.event.entities.EventMapper;
import com.evently.events.event.entities.EventRequestDto;
import com.evently.events.event.entities.EventResponseDto;
import com.evently.events.eventLocations.EventsLocationsService;
import com.evently.events.eventLocations.entities.EventLocationsDto;
import com.evently.events.eventsCategories.EventsCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventsCategoriesService eventsCategoriesService;
    private final EventMapper eventMapper;
    private final EventsLocationsService eventsLocationsService;
    private final EventRepository eventRepository;

    @Transactional
    //  @CachePut(value = "events", key = "'id:' + #result.getId()")
    public EventResponseDto create(EventRequestDto request) {
        Event event = eventMapper.toEntity(request);
        eventRepository.save(event);
        eventsCategoriesService.categorize(event, request.getCategories());
        List<EventLocationsDto> list = eventsLocationsService.create(event, request.getEventLocationData());
        
        return eventMapper.toResponseDto(event, request.getCategories(), list);
    }
//
//    // @Cacheable(value = "events", key = "'id:' + #id")
//    public EventResponseDto getById(long id) {
//        Event event = eventRepository.findByIdOrThrow(id);
//        List<EventLocationsDto> locationsBy = eventsLocationsService.getLocationsBy(id);
//        List<String> cat = eventsClassificationsService.getEventCategories(id);
//        return EventResponseDto.of(event, cat, locationsBy);
//    }
}