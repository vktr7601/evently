package com.company.ticket_service.event;

import com.company.ticket_service.category.entities.CategoryDto;
import com.company.ticket_service.core.EventRegistrationService;
import com.company.ticket_service.core.exceptions.ResourceNotFoundException;
import com.company.ticket_service.event.dto.EventDto;
import com.company.ticket_service.event.dto.EventRequest;
import com.company.ticket_service.eventCategories.EventsCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

  private final EventRepository eventRepository;
  private final EventRegistrationService eventRegistrationService;
  private final EventsCategoriesService eventsCategoriesService;
  private final EventMapper eventMapper;

  @Transactional
  @CachePut(value = "events", key = "'id:' + #result.id()")
  public EventDto create(EventRequest eventRequest) {
    return eventRegistrationService.create(eventRequest);
  }

  @Cacheable(value = "events", key = "'id:' + #id")
  public EventDto findById(Long id) {
    return eventRepository
        .findById(id)
        .map(
            event -> {
              List<String> eventCategories =
                  eventsCategoriesService.getEventCategories(id).stream()
                      .map(CategoryDto::name)
                      .toList();
              return eventMapper.toDTO(event, eventCategories);
            })
        .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
  }
}