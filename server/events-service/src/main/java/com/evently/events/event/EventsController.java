package com.evently.events.event;

import com.evently.events.event.entities.EventDetailsDto;
import com.evently.events.event.entities.EventListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventsController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventListDto>> listAllEvents() {
        log.info("Request received: Fetching all events sorted by date.");

        List<EventListDto> events = eventService.findAllEventsSortedByDateDesc();

        log.info("Response sent: Successfully fetched {} events.", events.size());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetailsDto> getEventDetails(@PathVariable Long id) {
        log.info("Request received: Fetching details for event ID: {}", id);

        EventDetailsDto eventDetailsDto = eventService.findById(id);

        log.info("Response sent: Successfully fetched details for event: {}", eventDetailsDto.getName());
        return ResponseEntity.ok(eventDetailsDto);
    }

//    @PostMapping
//    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventDto) {
//        EventResponseDto eventResponseDto = eventService.create(eventDto);
//        return new ResponseEntity<>(eventResponseDto, HttpStatus.CREATED);
//    }
}