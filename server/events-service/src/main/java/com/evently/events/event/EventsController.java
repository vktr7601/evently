package com.evently.events.event;

import com.evently.events.event.entities.EventResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventsController {
    @GetMapping
    public ResponseEntity<EventResponseDto> getAll() {
        EventResponseDto eventResponseDto = new EventResponseDto(1, "hello", null, null, null);
        return ResponseEntity.ok(eventResponseDto);
    }
}