package com.evently.booking.ticket;

import com.evently.booking.infrastructure.clients.eventsService.EventServiceClient;
import com.evently.booking.infrastructure.clients.eventsService.data.EventsLocationsDto;
import com.evently.booking.infrastructure.exceptions.TicketNotRefundableException;
import com.evently.booking.order.data.OrderStatus;
import com.evently.booking.ticket.data.TicketMapper;
import com.evently.booking.ticket.data.TicketStatus;
import com.evently.booking.ticket.entities.TicketListItem;
import dtos.TicketsCreated;
import events.eventCreated.TicketsCreationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final EventServiceClient eventServiceClient;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public void createTickets(List<TicketsCreationEvent> data) {
        if (data == null || data.isEmpty()) {
            log.warn("No ticket allocations provided. Skipping ticket " +
                    "creation.");
            return; // No data to process
        }
        List<Ticket> tickets = new ArrayList<>();

        List<TicketsCreationEvent> list = data.stream().map(x -> {
            if (ticketRepository.isPersisted(x.getEventLocationId(),
                    x.getEventStartTime())) {
                log.warn("Tickets for event location ID {} and date time {} " +
                                "already exist. Skipping creation.",
                        x.getEventLocationId(), x.getEventStartTime());
                return null; // Skip this ticket allocation
            }

            return x;

        }).toList();

        if (list.isEmpty() || list.get(0) == null) {
            log.warn("No new ticket allocations to create. All provided " +
                    "ticket allocations already exist in the database.");
            return; // No new tickets to create
        }

        for (TicketsCreationEvent ticketsCreationEvent : list) {
            for (int i = 0; i < ticketsCreationEvent.getTicketsCount(); i++) {
                Ticket ticket = ticketMapper.convert(ticketsCreationEvent);
                tickets.add(ticket);
            }
        }

        ticketRepository.saveAll(tickets);
        List<Long> eventLocationIds =
                data.stream().map(TicketsCreationEvent::getEventLocationId).toList();
        TicketsCreated ticketsCreated = new TicketsCreated();
        ticketsCreated.setEventLocationIds(eventLocationIds);
        eventPublisher.publishEvent(ticketsCreated);


    }

    public boolean checkAvailability(long locationEventsId, int ticketCounts) {
        return ticketRepository.hasAvailableSeats(locationEventsId,
                ticketCounts);
    }

    @Transactional
    public List<Ticket> getTicketsForEvent(long locationEventsId,
                                           int ticketCounts,
                                           LocalDateTime dateTime) {
        return ticketRepository.findAvailableTicketsForEvent(locationEventsId
                , dateTime, PageRequest.of(0, ticketCounts));
    }


    public List<TicketListItem> getUserTickets(long userId) {
        List<Ticket> tickets = ticketRepository.findAllByUserId(userId);

        var res = enrichTicketsWithEventDetails(tickets);
//        List<Long> extractEventsLocationsIdFromTicker =
//                extractEventsLocationsIdFromTickets(tickets);
//        List<Long> eventLocationIds =
//                tickets.stream().map(Ticket::getEventLocationsId).toList();
//
//        List<EventsLocationsDto> locations1 =
//                eventServiceClient.getLocations(eventLocationIds);
//        Map<Long, EventsLocationsDto> locationsMap =
//                locations1.stream().collect(Collectors.toMap
//                (EventsLocationsDto::getId, eventLocationDto ->
//                eventLocationDto));
//
////        Map<Long, EventsLocationsDto> locations =
////                eventsLocationsClient.getLocations(eventLocationIds);
//
//        List<TicketListItem> ticketListItems = tickets.stream().map(x -> {
//            var eventLocation = locationsMap.get(x.getId());
//            return ticketMapper.toListItem(x, eventLocation.getEventName(),
//                    eventLocation.getLocationName());
//        }).toList();

        log.info("Tickets for user ID {} has been saved", userId);
        return res;
    }

    public List<TicketListItem> getTicketsByOrderId(long orderId) {
        List<Ticket> tickets = ticketRepository.findAllByOrderId(orderId);
        var res = enrichTicketsWithEventDetails(tickets);
        log.info("Tickets for order ID {} has been saved", orderId);
        return res;
    }

    public void finalizeOrder(long orderId) {
        List<Ticket> tickets = ticketRepository.findAllByOrderId(orderId);
        for (Ticket ticket : tickets) {
            ticket.setStatus(TicketStatus.BOOKED);
        }

        ticketRepository.saveAll(tickets);
    }

    public void refundTicket(Long userId, long ticketId) {
        Ticket ticket = ticketRepository.findByUserIdAndTicketId(userId,
                ticketId);
        if (ticket.getEventStartTime().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new TicketNotRefundableException();
        }


        var order = ticket.getOrder();

        List<Ticket> tickets = order.getTickets();
        if (tickets.size() == 1) {
            order.setStatus(OrderStatus.REFUNDED);
        }
    }

    public Ticket findTicketById(long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
    }

    public Ticket findAllTicketsForSpecificEventLocation(long eventLocations) {
        List<Ticket> affectedTickets =
                ticketRepository.findAllByEventLocationsId(eventLocations);

        List<Ticket> noOrderTickets =
                affectedTickets.stream().filter(x -> x.getOrder() == null).toList();
        noOrderTickets.forEach(x -> {
            x.setStatus(TicketStatus.CANCELED);
        });

        for (Ticket ticket : affectedTickets) {
            if (ticket.getOrder() == null) {
                ticket.setStatus(TicketStatus.CANCELED);
                ticketRepository.save(ticket);
            } else {
            }
        }

        return null;
    }


    public int discardAllUnboughtTickets(List<Long> ids) {
        int discardedTickets = ticketRepository.discardAllUnboughtTickets(ids);

        log.info("Tickets discarded {} has been saved", discardedTickets);
        return discardedTickets;
    }

    private List<TicketListItem> enrichTicketsWithEventDetails(List<Ticket> ticketList) {
        List<Long> eventLocationIds = extractEventLocationIds(ticketList);
        List<EventsLocationsDto> locations =
                eventServiceClient.getLocations(eventLocationIds);
        Map<Long, EventsLocationsDto> locationsMap =
                toEventLocationsMap(locations);

        List<TicketListItem> listItems = ticketList.stream().map(ticket -> {
            var eventLocation = locationsMap.get(ticket.getEventLocationsId());
            return ticketMapper.toListItem(ticket, eventLocation.getEventName(),
                    eventLocation.getLocationName());
        }).toList();


        return listItems;
    }

    private Map<Long, EventsLocationsDto> toEventLocationsMap(List<EventsLocationsDto> eventsLocations) {
        log.info("Building event locations map for {} locations",
                eventsLocations.size());
        return eventsLocations.stream()
                .collect(Collectors.toMap(EventsLocationsDto::getId,
                        Function.identity()));
    }

    private List<Long> extractEventLocationIds(List<Ticket> tickets) {
        return tickets.stream()
                .map(Ticket::getEventLocationsId)
                .toList();
    }

}