package com.company.ticket_service.core;

import com.company.ticket_service.core.exceptions.DuplicateResourceException;
import com.company.ticket_service.event.Event;
import com.company.ticket_service.event.EventMapper;
import com.company.ticket_service.event.EventRepository;
import com.company.ticket_service.event.dto.EventDto;
import com.company.ticket_service.event.dto.EventRequest;
import com.company.ticket_service.eventCategories.EventsClassificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventRegistrationService {

    private final EventsClassificationsService eventsClassificationsService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Transactional
    public EventDto create(EventRequest request) {
        if (eventRepository.existsByName(request.name()))
            throw new DuplicateResourceException("Event with name " + request.name() + " already exists");

        Event saved = eventRepository.save(eventMapper.toEntity(request));
        eventsClassificationsService.categorize(saved, request.categories());

        return eventMapper.toDTO(saved, request.categories());
    }
}