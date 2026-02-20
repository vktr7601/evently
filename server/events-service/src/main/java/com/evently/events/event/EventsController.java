package com.evently.events.event;

import com.evently.events.event.entities.CreateEventRequest;
import com.evently.events.event.entities.EventDetailDto;
import com.evently.events.event.entities.EventListItemDto;
import com.evently.events.event.entities.UpdateEventRequest;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventsController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventListItemDto>> getAllEvents() {
        log.info("Request received: Fetching all events sorted by date.");

        List<EventListItemDto> events = eventService.findAllEventsSortedByDateDesc();

        log.info("Response sent: Successfully fetched {} events.", events.size());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetailDto> getEventDetails(@PathVariable Long id) {
        log.info("Request received: Fetching details for event ID: {}", id);

        EventDetailDto eventDetailDto = eventService.findEventDetailsById(id);

        log.info("Response sent: Successfully fetched details for event: {}", eventDetailDto.getEventName());
        return ResponseEntity.ok(eventDetailDto);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventListItemDto>> listAllEventsByCategory(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(eventService.findAllEventsByCategoryName(category));
    }

    @PostMapping
    public ResponseEntity<EventDetailDto> createEvent(@Valid @RequestBody CreateEventRequest createEventRequest) {
        EventDetailDto createdEvent = eventService.createEvent(createEventRequest);

        log.info("Response sent: Successfully created event: {}", createdEvent.getEventName());

        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDetailDto> updateEvent(@PathVariable Long id, @RequestBody UpdateEventRequest updateEventRequest) {
        EventDetailDto eventDetailDto = eventService.updateEvent(id, updateEventRequest);


        return ResponseEntity.ok(eventDetailDto);
    }


    @GetMapping("/{id}/location")
    public ResponseEntity<EventsLocationsDto> getLocation(@PathVariable Long id) {
        EventsLocationsDto eventsLocationsDto = eventService.getEventLocationData(id);
        log.info("Response sent: Successfully fetched location details for event ID: {}", id);

        return ResponseEntity.ok(eventsLocationsDto);
    }


    @PostMapping("/clear-past-events")
    public ResponseEntity<String> triggerEventsClearing() {
        var processedIds = eventService.clearHistoryEvents();

        return ResponseEntity.ok("Successfully cleared past events. Processed event IDs: " + processedIds);
    }
}