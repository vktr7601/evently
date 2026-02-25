package com.evently.booking.ticket.controller;

import com.evently.booking.ticket.dto.TicketListItem;
import com.evently.booking.ticket.service.TicketService;
import constants.ApplicationHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(@RequestParam("eventLocationId"
    ) long eventLocationId, @RequestParam("ticketsCount") int ticketsCount) {
        boolean result = ticketService.checkAvailability(eventLocationId,
                ticketsCount);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(409).body("Not enough tickets " +
                    "available for the requested event location.");
        }
    }

    @GetMapping
    public List<TicketListItem> getUserTickets(@RequestHeader(ApplicationHeaders.USER_ID) Long userId) {
        return ticketService.getUserTickets(userId);
    }

    @GetMapping("/available-count")
    public ResponseEntity<Integer> getAvailableTickets(@RequestParam(
            "eventLocationId") long eventLocationId) {
        int availableTickets =
                ticketService.getAvailableTicketsCount(eventLocationId);
        return ResponseEntity.ok().body(availableTickets);
    }
}