package com.company.ticket_service.event;

import com.company.ticket_service.event.entities.EventRequestDto;
import com.company.ticket_service.event.entities.EventResponseDto;
import com.company.ticket_service.eventsLocations.EventsLocationsService;
import com.company.ticket_service.eventsLocations.entities.EventOccurrenceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventsLocationsService eventsLocationsService;
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventOccurrenceDTO>> getEventsByLocation(@RequestParam(name = "locationName") String locationName) {
        List<EventOccurrenceDTO> events = eventsLocationsService.getEventsByLocation(locationName);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto request) {
        var result = eventService.create(request);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
