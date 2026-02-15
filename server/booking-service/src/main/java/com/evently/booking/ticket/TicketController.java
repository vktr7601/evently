package com.evently.booking.ticket;

import dtos.TicketAllocation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final TicketService ticketService;

    @PostMapping("/create-tickets")
    public boolean createTickets(@RequestBody List<TicketAllocation> data) {
        return true;
    }

    @PostMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestBody CheckAvailabilityRequest checkAvailabilityRequest) {
        ticketService.checkAvailability(checkAvailabilityRequest.getEventsLocationsId(), checkAvailabilityRequest.getTickets());

        return ResponseEntity.ok().build();
    }
}