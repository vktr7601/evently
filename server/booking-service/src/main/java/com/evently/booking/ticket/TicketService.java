package com.evently.booking.ticket;

import com.evently.booking.exceptions.InsufficientTicketException;
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


    public void checkAvailability(int locationEventsId, int ticketCounts) {


        boolean available = ticketRepository.hasAvailableSeats(locationEventsId, ticketCounts);
        if (!available) {
            throw new InsufficientTicketException(locationEventsId, ticketCounts);
        }

        // if available then we will reserve the tickets for the user and then we will ask the user to make the payment and if the payment is successful then we will make the tickets active and if the payment is not successful then we will make the tickets available again.
        // check avaailnaility
        // unfortunately, we cannot guarantee the availability of the tickets at this point because of the time gap between checking and booking, so we will have to check the availability again at the time of booking and if the tickets are not available then we will have to inform the user that the tickets are not available and ask them to try again later.
        //user click book
        //1. create the tickets
        // assing the user
        // assing status
        // active = false;
        //2. create the payment
        //3. if payment successfull then active = true;
        // ticketStays = SOLD
        // CRON JOBS CHECKS FOR THE RESERVED TICKETS AND IF THE PAYMENT IS NOT DONE THEN MAKE THE TICKETS AVAILABLE AGAIN

    }
}