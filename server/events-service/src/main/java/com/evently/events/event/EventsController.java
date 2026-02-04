package com.evently.events.event;

import com.evently.events.event.entities.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;
    
    @GetMapping
    public ResponseEntity<List<EventDto>> getAll() {
        List<EventDto> events = eventService.findAll();
        return ResponseEntity.ok(events);
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<EventDto> getById(@PathVariable long id) {
//        eventService
//        return null;
//    }
}