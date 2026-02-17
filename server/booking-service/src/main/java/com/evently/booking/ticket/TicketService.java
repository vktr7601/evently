package com.evently.booking.ticket;

import com.evently.booking.exceptions.InsufficientTicketException;
import com.evently.booking.order.EventsLocationsClient;
import com.evently.booking.order.entities.EventsLocationsDto;
import com.evently.booking.ticket.entities.TicketListItem;
import com.evently.booking.ticket.entities.TicketMapper;
import com.evently.booking.ticket.entities.TicketStatus;
import dtos.TicketAllocation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final EventsLocationsClient eventsLocationsClient;


    public boolean createTickets(List<TicketAllocation> data) {
        List<Ticket> tickets = new ArrayList<>();

        List<TicketAllocation> list = data.stream().map(x -> {
            if (ticketRepository.isPersisted(x.getEventLocationId(), x.getDateTime())) {
                log.warn("Tickets for event location ID {} and date time {} already exist. Skipping creation.", x.getEventLocationId(), x.getDateTime());
                return null; // Skip this ticket allocation
            }

            return x;
        }).toList();

        if (list.isEmpty() || list.get(0) == null) {
            log.warn("No new ticket allocations to create. All provided ticket allocations already exist in the database.");
            return false; // No new tickets to create
        }

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

    public List<Ticket> getTicketsForEvent(long locationEventsId, int ticketCounts, LocalDateTime dateTime) {
        var availableTickets = ticketRepository.findAvailableTicketsForEvent(locationEventsId, dateTime, ticketCounts);
        if (availableTickets.size() < ticketCounts) {
            throw new InsufficientTicketException(locationEventsId, ticketCounts);
        }

        return availableTickets;
    }

    public void save(List<Ticket> tickets) {
        ticketRepository.saveAll(tickets);
    }


    public List<TicketListItem> getTicketsByOrderId(long orderId) {
        List<Ticket> tickets = ticketRepository.findAllByOrderId(orderId);
        List<Long> eventLocationIds = tickets.stream().map(Ticket::getEventLocationsId).toList();

        Map<Long, EventsLocationsDto> locations = eventsLocationsClient.getLocations(eventLocationIds);

        List<TicketListItem> ticketListItems = tickets.stream().map(x -> {
            var eventLocation = locations.get(x.getEventLocationsId());
            return ticketMapper.toListItem(x, eventLocation.getEventName(), eventLocation.getLocationName());
        }).toList();

        log.info("Tickets for order ID {} has been saved", orderId);
        return ticketListItems;
    }

    public void finalizeOrder(long orderId) {
        List<Ticket> tickets = ticketRepository.findAllByOrderId(orderId);
        for (Ticket ticket : tickets) {
            ticket.setStatus(TicketStatus.BOOKED);
            ticket.setReservedUntil(null);
        }

        ticketRepository.saveAll(tickets);
    }

}