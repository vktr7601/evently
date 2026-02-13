package com.evently.events.event;

import com.evently.events.event.entities.EventDetailDto;
import com.evently.events.event.entities.EventListItemDto;
import com.evently.events.event.entities.EventRequestDto;
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

        log.info("Response sent: Successfully fetched details for event: {}", eventDetailDto.getName());
        return ResponseEntity.ok(eventDetailDto);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventListItemDto>> listAllEventsByCategory(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(eventService.findAllEventsByCategoryName(category));
    }

    @PostMapping
    public ResponseEntity<EventDetailDto> createEvent(@RequestBody EventRequestDto eventRequestDto) {
        EventDetailDto createdEvent = eventService.createEvent(eventRequestDto);

        log.info("Response sent: Successfully created event: {}", createdEvent.getName());

        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }


//    @PostMapping
//    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventDto) {
//        EventResponseDto eventResponseDto = eventService.create(eventDto);
//        return new ResponseEntity<>(eventResponseDto, HttpStatus.CREATED);
//    }
}