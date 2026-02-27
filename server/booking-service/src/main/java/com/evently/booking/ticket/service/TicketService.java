package com.evently.booking.ticket.service;

import com.evently.booking.infrastructure.clients.eventsService.EventServiceClient;
import com.evently.booking.infrastructure.clients.eventsService.data.EventsLocationsDto;
import com.evently.booking.infrastructure.clients.paymentService.PaymentServiceClient;
import com.evently.booking.infrastructure.clients.paymentService.data.RefundRequest;
import com.evently.booking.infrastructure.exceptions.TicketNotRefundableException;
import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.ticket.data.TicketMapper;
import com.evently.booking.ticket.dto.TicketListItem;
import com.evently.booking.ticket.model.Ticket;
import com.evently.booking.ticket.model.TicketStatus;
import com.evently.booking.ticket.repository.TicketRepository;
import events.event.EventCreated;
import events.event.EventTicketsBulkUpdate;
import events.event.EventTicketsUpdate;
import events.ticket.TicketsCreated;
import events.ticket.TicketsCreationEvent;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketsMapper;
    private final EventServiceClient eventServiceClient;
    private final ApplicationEventPublisher eventPublisher;
    private final TemplateEngine templateEngine;
    private final PaymentServiceClient paymentService;

    @Transactional
    public void createTickets(EventCreated eventCreated) {
        List<Ticket> tickets = new ArrayList<>();
        for (TicketsCreationEvent ticketsCreationEvent :
                eventCreated.getTicketsCreationEvents()) {
            for (int i = 0; i < ticketsCreationEvent.getTicketsCount(); i++) {
                Ticket ticket = ticketsMapper.convert(ticketsCreationEvent);
                tickets.add(ticket);
            }
        }

        ticketRepository.saveAll(tickets);
        List<Long> eventLocationIds =
                eventCreated.getTicketsCreationEvents().stream().map(TicketsCreationEvent::getEventLocationId).toList();
        TicketsCreated ticketsCreated = new TicketsCreated();
        ticketsCreated.setEventLocationIds(eventLocationIds);
        eventPublisher.publishEvent(ticketsCreated);
    }

    @Transactional
    public void createTickets(List<TicketsCreationEvent> data) {
        LocalDateTime now = LocalDateTime.now();
        log.info("Start Time" + LocalDateTime.now());

        if (data == null || data.isEmpty()) {
            log.warn("No ticket allocations provided. Skipping ticket " +
                    "creation.");
            return; // No data to process
        }
        List<Ticket> tickets = new ArrayList<>();

        List<TicketsCreationEvent> list = data.stream().map(x -> {
            if (ticketRepository.isPersisted(x.getEventLocationId(),
                    x.getEventStartTime())) {
                log.warn("Tickets for event location ID {} and date time {} " + "already exist. Skipping creation.", x.getEventLocationId(), x.getEventStartTime());
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
                Ticket ticket = ticketsMapper.convert(ticketsCreationEvent);
                tickets.add(ticket);
            }
        }

        ticketRepository.saveAll(tickets);
        List<Long> eventLocationIds =
                data.stream().map(TicketsCreationEvent::getEventLocationId).toList();
        TicketsCreated ticketsCreated = new TicketsCreated();
        ticketsCreated.setEventLocationIds(eventLocationIds);
        eventPublisher.publishEvent(ticketsCreated);

        log.info("End Time" + LocalDateTime.now());


        LocalDateTime now1 = LocalDateTime.now();
        Duration res = Duration.between(now, now1);
        log.info("Total tickets created: " + res);
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

    public int getAvailableTicketsCount(long locationEventsId) {
        return ticketRepository.countByLocationAndStatus(locationEventsId,
                TicketStatus.AVAILABLE);
    }

    @Transactional
    public List<TicketListItem> getUserTickets(long userId) {
        List<Ticket> tickets = ticketRepository.findAllByUserId(userId);

        List<TicketListItem> ticketListItems =
                enrichTicketsWithEventDetails(tickets);

        return ticketListItems;
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

    @Transactional
    public void refundTicket(Long userId, long ticketId) throws TicketNotRefundableException {
        Ticket ticket = ticketRepository.findByUserIdAndTicketId(userId,
                ticketId);

        if (!LocalDateTime.now().isBefore(ticket.getEventStartTime().minusDays(1))) {
            throw new TicketNotRefundableException();
        }


        Order order = ticket.getOrder();

        List<Ticket> tickets = order.getTickets();
        if (tickets.size() == 1) {
            order.setStatus(OrderStatus.REFUNDED);
        }
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderNumber(order.getNumber().toString());
        refundRequest.setReason("user requested");
        refundRequest.setTransactionId(order.getTransactionId());
        paymentService.processRefund(refundRequest);
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

    List<TicketListItem> enrichTicketsWithEventDetails(List<Ticket> ticketList) {
        List<Long> eventLocationIds = extractEventLocationIds(ticketList);

        List<EventsLocationsDto> locations =
                eventServiceClient.getLocations(eventLocationIds);

        Map<Long, EventsLocationsDto> locationsMap =
                toEventLocationsMap(locations);

        return ticketList.stream().map(ticket -> {
            var eventLocation = locationsMap.get(ticket.getEventLocationsId());
            return ticketsMapper.toListItem(ticket,
                    eventLocation.getEventName(),
                    eventLocation.getLocationName());
        }).toList();
    }

    Map<Long, EventsLocationsDto> toEventLocationsMap(List<EventsLocationsDto> eventsLocations) {
        log.info("Building event locations map for {} locations",
                eventsLocations.size());
        return eventsLocations.stream().collect(Collectors.toMap(EventsLocationsDto::getId, Function.identity()));
    }

    private List<Long> extractEventLocationIds(List<Ticket> tickets) {
        return tickets.stream().map(Ticket::getEventLocationsId).toList();
    }

    @Transactional
    public void processBulkUpdate(EventTicketsBulkUpdate bulkEvent) {
        log.info("Processing bulk update for Event ID: {} with {} location " + "updates", bulkEvent.getEventId(), bulkEvent.getUpdates().size());

        for (EventTicketsUpdate update : bulkEvent.getUpdates()) {
            long locationId = update.getEventLocationId();

            if (update.isDateUpdated()) {
                log.debug("Updating start time for location {} to {}",
                        locationId, update.getNewStartTime());
                ticketRepository.updateStartTimeByLocationId(locationId,
                        update.getNewStartTime());
            }

            if (update.isTicketCountUpdated()) {
                int currentCount =
                        ticketRepository.countByEventLocationId(update.getEventLocationId());
                long targetCount = update.getNewTicketsCount();
                long delta = targetCount - currentCount;

                if (delta == 0) return;

                if (delta > 0) {
                    log.info("Adding {} new tickets for location {}", delta,
                            locationId);

                    Ticket template =
                            ticketRepository.findFirstByEventLocationsId(locationId).orElseThrow(() -> new IllegalStateException("Base ticket not found for " + "location " + locationId));

                    List<Ticket> newTickets = new ArrayList<>();
                    for (int i = 0; i < delta; i++) {
                        Ticket ticket = new Ticket();
                        ticket.setEventLocationsId(locationId);
                        ticket.setNumber(UUID.randomUUID());
                        ticket.setEventStartTime(template.getEventStartTime());
                        ticket.setPrice(template.getPrice());
                        newTickets.add(ticket);
                    }
                    ticketRepository.saveAll(newTickets);

                }
            }

            if (update.isPriceUpdated()) {
                List<Ticket> newTickets =
                        ticketRepository.findAllByEventLocationsId(locationId);
                List<Ticket> updatedTickets = new ArrayList<>();
                for (Ticket ticket : newTickets) {
                    if (ticket.getStatus().equals(TicketStatus.AVAILABLE) || ticket.getStatus().equals(TicketStatus.PENDING_PAYMENT)) {
                        ticket.setPrice(update.getNewPrice());

                        updatedTickets.add(ticket);
                    }
                }

                ticketRepository.saveAll(updatedTickets);
            }
        }
    }

    @Transactional
    public TicketListItem getTicket(Long id) {
        Ticket ticket =
                ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found for id " + id));

        return enrichTicketsWithEventDetails(List.of(ticket)).get(0);
    }


    public String fillTicketTemplate(TicketListItem ticketListItem) {
        Context context = new Context();
        context.setVariable("eventName", ticketListItem.getEventName());
        context.setVariable("locationName",
                ticketListItem.getEventLocationName());
        context.setVariable("eventDate", ticketListItem.getEventStartTime());
        context.setVariable("eventTime", ticketListItem.getEventStartTime());
        context.setVariable("ticketNumber", ticketListItem.getNumber());

        String html = templateEngine.process("ticket-template", context);


        return html;
    }
}