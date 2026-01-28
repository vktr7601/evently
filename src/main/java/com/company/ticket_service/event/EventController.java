package com.company.ticket_service.event;

import com.company.ticket_service.core.TicketBookingService;
import com.company.ticket_service.event.dto.EventDto;
import com.company.ticket_service.event.dto.EventRequest;
import com.company.ticket_service.ticket.data.TicketRequest;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final TicketBookingService ticketBookingService;

    @PostMapping
    public ResponseEntity<EventDto> create(@Valid @RequestBody EventRequest eventRequest, Authentication authentication) {
        var event = eventService.create(eventRequest);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDto> get(@PathVariable long id) {
        return new ResponseEntity<>(eventService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/{eventId}/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void bookTicket(@PathVariable Long eventId, @RequestBody TicketRequest request) {
        ticketBookingService.bookTicket(eventId, request);
    }
}