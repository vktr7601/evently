package com.evently.booking.ticket;

import com.evently.booking.ticket.entities.TicketListItem;
import dtos.Headers;
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
    public ResponseEntity<?> checkAvailability(@RequestParam("eventLocationId") long eventLocationId, @RequestParam("ticketsCount") int ticketsCount) {
        ticketService.checkAvailability(eventLocationId, ticketsCount);

        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public List<TicketListItem> getUserTickets(@RequestHeader(Headers.USER_ID) Long userId) {
        return ticketService.getUserTickets(userId);
    }

    public void refundTicketRequest(@RequestHeader(Headers.USER_ID) Long userId, int ticketId) {
        ticketService.refundTicket(userId, ticketId);
    }
}