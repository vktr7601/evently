package com.evently.events.event;

import com.evently.events.config.KafkaProducer;
import com.evently.events.event.entities.EventDto;
import com.evently.events.event.entities.EventRequestDto;
import com.evently.events.event.entities.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;
    private final KafkaProducer kafkaProducer;

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

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventDto) {
        EventResponseDto eventResponseDto = eventService.create(eventDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.CREATED);
    }
}