package com.evently.booking.ticket;

import dtos.CreateTicketsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TicketController {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @PostMapping("/create-tickets")
    public boolean createTickets(@RequestBody List<CreateTicketsDto> data) {
        return true;
    }

    @PostMapping("/check-availability")
    public void checkAvailability(@RequestBody CheckAvailabilityRequest checkAvailabilityRequest) {

    }
}