package com.evently.events.event.controller;

import com.evently.events.event.dto.EventDetailDto;
import com.evently.events.event.dto.EventListItemDto;
import com.evently.events.event.dto.UserEventsLocations;
import com.evently.events.event.service.EventService;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import constants.ApplicationHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        List<EventListItemDto> events =
                eventService.findAllEventsSortedByDateDesc();

        log.info("Response sent: Successfully fetched {} events.",
                events.size());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetailDto> getEventDetails(@PathVariable Long id) {
        log.info("Request received: Fetching details for event ID: {}", id);

        EventDetailDto eventDetailDto = eventService.findEventDetailsById(id);

        log.info("Response sent: Successfully fetched details for event: {}",
                eventDetailDto.getEventName());
        return ResponseEntity.ok(eventDetailDto);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventListItemDto>> listAllEventsByCategory(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(eventService.findAllEventsByCategoryName(category));
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<EventsLocationsDto> getLocation(@PathVariable Long id) {
        EventsLocationsDto eventsLocationsDto =
                eventService.getEventLocationData(id);
        log.info("Response sent: Successfully fetched location details for " +
                "event ID: {}", id);

        return ResponseEntity.ok(eventsLocationsDto);
    }

    @GetMapping("/feed")
    public ResponseEntity<UserEventsLocations> getUserFeed(@RequestHeader(ApplicationHeaders.USER_ID) long userId) {
        return null;
    }
}