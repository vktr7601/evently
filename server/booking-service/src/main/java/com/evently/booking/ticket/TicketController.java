package com.evently.booking.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(@RequestParam("eventLocationId") long eventLocationId, @RequestParam("ticketsCount") int ticketsCount) {
        ticketService.checkAvailability(eventLocationId, ticketsCount);

        return ResponseEntity.noContent().build();
    }
}