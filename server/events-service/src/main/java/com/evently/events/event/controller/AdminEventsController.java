package com.evently.events.event.controller;

import com.evently.events.event.dto.request.EventCreateRequest;
import com.evently.events.event.dto.EventDetailDto;
import com.evently.events.event.dto.request.EventUpdateRequest;
import com.evently.events.event.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventsController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventDetailDto> createEvent(@Valid @RequestBody EventCreateRequest createEventRequest) {
        EventDetailDto createdEvent =
                eventService.createEvent(createEventRequest);
        log.info("Response sent: Successfully created event: {}",
                createdEvent.getEventName());
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDetailDto> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventUpdateRequest updateEventRequest) {
        EventDetailDto eventDetailDto = eventService.updateEvent(id,
                updateEventRequest);
        log.info("Response sent: Successfully updated event ID: {}", id);
        return ResponseEntity.ok(eventDetailDto);
    }


    @PostMapping("/clear-past-events")
    public ResponseEntity<String> triggerEventsClearing() {
        var processedIds = eventService.clearHistoryEvents();

        return ResponseEntity.ok("Successfully cleared past events. Processed" +
                " event IDs: " + processedIds);
    }
}