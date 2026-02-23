package com.evently.booking.ticket;

import com.evently.booking.order.OrderService;
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
    private final OrderService orderService;

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
    public List<TicketListItem> getUserTickets(@RequestHeader(Headers.USER_ID) Long userId) {
        return ticketService.getUserTickets(userId);
    }

    @GetMapping("/available-count")
    public ResponseEntity<Integer> getAvailableTickets(@RequestParam(
            "eventLocationId") long eventLocationId) {
        int availableTickets =
                ticketService.getAvailableTicketsCount(eventLocationId);
        return ResponseEntity.ok().body(availableTickets);
    }

    public void refundTicketRequest(@RequestHeader(Headers.USER_ID) Long userId, int ticketId) {
        ticketService.refundTicket(userId, ticketId);
    }


    public void handleEventCancellationEvent(int eventLocations) {
        Ticket allTicketsForSpecificEventLocation =
                ticketService.findAllTicketsForSpecificEventLocation(eventLocations);
    }
}