package com.company.ticket_service.eventsLocations;

import com.company.ticket_service.eventsLocations.entities.EventOccurrenceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventsLocationsService {

    private final EventsLocationsRepository eventsLocationsRepository;

    /**
     * Get all event occurrences for a specific category/classification
     *
     * @param classificationName Name of the classification (e.g., "Theater", "Sports")
     * @return List of event occurrences with location, date, and event details
     */
    @Cacheable(value = "eventClassification", key = "#classificationName")
    public List<EventOccurrenceDTO> getEventsByClassification(String classificationName) {
        return eventsLocationsRepository.findAllByClassificationName(classificationName);
    }

    @Cacheable(value = "eventClassification", key = "#location")
    public List<EventOccurrenceDTO> getEventsByLocation(String location) {
        return eventsLocationsRepository.finaAllByLocationName(location);
    }
}
