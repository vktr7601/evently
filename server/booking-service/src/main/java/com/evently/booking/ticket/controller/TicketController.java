package com.evently.booking.ticket.controller;

import com.evently.booking.ticket.dto.TicketListItem;
import com.evently.booking.ticket.service.TicketService;
import constants.ApplicationHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    public ResponseEntity<List<TicketListItem>> getUserTickets(@RequestHeader(ApplicationHeaders.USER_ID) long userId) {
        List<TicketListItem> userTickets = ticketService.getUserTickets(userId);

        return ResponseEntity.ok(userTickets);
    }

    @GetMapping("/available-count")
    public ResponseEntity<Integer> getAvailableTickets(@RequestParam(
            "eventLocationId") long eventLocationId) {
        int availableTickets =
                ticketService.getAvailableTicketsCount(eventLocationId);
        return ResponseEntity.ok().body(availableTickets);
    }

    @GetMapping(value = "/view/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> viewTicket(@PathVariable("id") Long id) {
        TicketListItem ticketListItem = ticketService.getTicket(id);
        String htmlContent = ticketService.fillTicketTemplate(ticketListItem);
        return ResponseEntity.ok(htmlContent);
    }

    @GetMapping(value = "/view/{id}/pdf", produces =
            MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadTicketPdf(@PathVariable("id") Long id) {
        TicketListItem ticketListItem = ticketService.getTicket(id);
        String htmlContent = ticketService.fillTicketTemplate(ticketListItem);

        byte[] pdfBytes = ticketService.pdfGeneration(htmlContent);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"ticket-" + id + ".pdf\"")
                .body(pdfBytes);
    }
}