package com.evently.booking.ticket;

import com.evently.booking.exceptions.InsufficientTicketException;
import com.evently.booking.ticket.entities.TicketMapper;
import dtos.TicketAllocation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public boolean createTickets(List<TicketAllocation> data) {
        List<Ticket> tickets = new ArrayList<>();


        List<TicketAllocation> list = data.stream().map(x -> {
            if (ticketRepository.isPersisted(x.getEventLocationId(), x.getDateTime())) {
                log.warn("Tickets for event location ID {} and date time {} already exist. Skipping creation.", x.getEventLocationId(), x.getDateTime());
                return null; // Skip this ticket allocation
            }

            return x;
        }).toList();

        for (TicketAllocation ticketAllocation : list) {

            for (int i = 0; i < ticketAllocation.getTicketsCount(); i++) {
                Ticket ticket = ticketMapper.convert(ticketAllocation);
                tickets.add(ticket);
            }
        }
        System.out.println();

        ticketRepository.saveAll(tickets);

        return true;
    }


    public void checkAvailability(long locationEventsId, int ticketCounts) {
        boolean available = ticketRepository.hasAvailableSeats(locationEventsId, ticketCounts);
        if (!available) {
            throw new InsufficientTicketException(locationEventsId, ticketCounts);
        }
    }
}