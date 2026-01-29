package com.company.ticket_service.event;

import com.company.ticket_service.event.dto.EventRequestDto;
import com.company.ticket_service.event.dto.EventResponseDto;
import com.company.ticket_service.eventClassification.EventsClassificationsService;
import com.company.ticket_service.eventsLocations.EventsLocationsService;
import com.company.ticket_service.eventsLocations.entities.EventLocationsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventsClassificationsService eventsClassificationsService;
    private final EventMapper eventMapper;
    private final EventsLocationsService eventsLocationsService;
    private final EventRepository eventRepository;

    @Transactional
    @CachePut(value = "events", key = "'id:' + #result.getId()")
    public EventResponseDto create(EventRequestDto request) {
        Event event = eventMapper.toEntity(request);
        eventRepository.save(event);
        eventsClassificationsService.categorize(event, request.getClassifications());
        List<EventLocationsDto> list = eventsLocationsService.create(event, request.getEventLocationData());
        return EventResponseDto.of(event, request.getClassifications(), list);
    }

    @Cacheable(value = "events", key = "'id:' + #id")
    public EventResponseDto getById(long id) {
        Event event = eventRepository.findByIdOrThrow(id);
        List<EventLocationsDto> locationsBy = eventsLocationsService.getLocationsBy(id);
        List<String> cat = eventsClassificationsService.getEventCategories(id);
        return EventResponseDto.of(event, cat, locationsBy);
    }
}
