package com.evently.booking.ticket;

import dtos.CreateTicketsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TicketController {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @PostMapping("/create-tickets")
    public boolean createTickets(@RequestBody List<CreateTicketsDto> data) {
        for (CreateTicketsDto ticket : data) {
            List<Ticket> tickets = new ArrayList<>();
            for (int i = 0; i < ticket.ticketsCount(); i++) {
                Ticket ticket1 = new Ticket();
                ticket1.setEventVenueId(ticket.eventVenueId());
                ticket1.setDateTime(ticket.dateTime());
                tickets.add(ticket1);
            }
            
            ticketRepository.saveAll(tickets);
        }

        return true;
    }
}
