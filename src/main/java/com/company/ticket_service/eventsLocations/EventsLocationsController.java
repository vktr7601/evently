package com.company.ticket_service.eventsLocations;

import com.company.ticket_service.eventsLocations.entities.EventOccurrenceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event-occurrences")
@RequiredArgsConstructor
public class EventsLocationsController {

    private final EventsLocationsService eventsLocationsService;

    @GetMapping
    public ResponseEntity<List<EventOccurrenceDTO>> getEventsByClassification(
            @RequestParam(name = "classification") String classificationName) {
        List<EventOccurrenceDTO> events = eventsLocationsService.getEventsByClassification(classificationName);
        return ResponseEntity.ok(events);
    }
}
