package com.company.ticket_service.eventClassification;

import com.company.ticket_service.classification.Classification;
import com.company.ticket_service.classification.ClassificationRepository;
import com.company.ticket_service.core.exceptions.ResourceNotFoundException;
import com.company.ticket_service.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventsClassificationsService {
    private final EventsClassificationsRepository eventsCategoriesRepository;
    private final ClassificationRepository classificationRepository;

    public void categorize(Event event, List<String> classifications) {
        List<Classification> fetchedCategories = classificationRepository.findAllByNameIn(classifications);
        if (fetchedCategories.size() != classifications.size())
            throw new ResourceNotFoundException("Category not found");

        List<EventsClassifications> mapping = fetchedCategories.stream().map(x -> eventsClassifications(event, x)).toList();

        eventsCategoriesRepository.saveAll(mapping);
    }

    public List<String> getEventCategories(long id) {
        return eventsCategoriesRepository.findClassificationNamesByEventId(id);
    }

    private EventsClassifications eventsClassifications(Event event, Classification classification) {
        EventsClassifications eventsCategories = new EventsClassifications();
        eventsCategories.setEvent(event);
        eventsCategories.setClassification(classification);
        return eventsCategories;
    }
}
