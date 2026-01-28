package com.company.ticket_service.eventClassification;

import java.util.List;

import com.company.ticket_service.classification.Classification;
import com.company.ticket_service.classification.ClassificationMapper;
import com.company.ticket_service.classification.ClassificationRepository;
import com.company.ticket_service.classification.entities.ClassificationDto;
import com.company.ticket_service.core.exceptions.ResourceNotFoundException;
import com.company.ticket_service.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventsClassificationsService {
    private final EventsClassificationsRepository eventsCategoriesRepository;
    private final ClassificationRepository classificationRepository;
    private final ClassificationMapper classificationMapper;

    @Transactional
    @CacheEvict(value = "events", key = "#event.id")
    public void categorize(Event event, List<String> categoryNames) {
        List<Classification> fetchedCategories = classificationRepository.findAllByNameIn(categoryNames);
        if (fetchedCategories.size() != categoryNames.size())
            throw new ResourceNotFoundException("Category not found");

        List<EventsClassifications> mapping =
                fetchedCategories.stream()
                        .map(
                                classification -> {
                                    EventsClassifications eventsCategories = new EventsClassifications();
                                    eventsCategories.setEvent(event);
                                    eventsCategories.setClassification(classification);
                                    return eventsCategories;
                                })
                        .toList();

        eventsCategoriesRepository.saveAll(mapping);
    }

    public List<ClassificationDto> getEventClassifications(long eventId) {
        return eventsCategoriesRepository.findAllByEventId(eventId).stream()
                .map(eventCategory -> classificationMapper.toDto(eventCategory.getClassification()))
                .toList();
    }
}