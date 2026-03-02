package com.evently.booking.ticket.service;

import com.evently.booking.infrastructure.clients.eventsService.EventServiceClient;
import com.evently.booking.infrastructure.clients.eventsService.data.EventsLocationsDto;
import com.evently.booking.order.model.Order;
import com.evently.booking.ticket.data.TicketMapper;
import com.evently.booking.ticket.dto.TicketListItem;
import com.evently.booking.ticket.model.Ticket;
import com.evently.booking.ticket.model.TicketStatus;
import com.evently.booking.ticket.repository.TicketRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.*;
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

    @Transactional
    public void addTickets(List<TicketsCreationEvent> ticketsCreationEvents) {
        List<Ticket> newlyCreatedTickets = new ArrayList<>();
        for (TicketsCreationEvent ticketsCreationEvent :
                ticketsCreationEvents) {
            for (int i = 0; i < ticketsCreationEvent.getTicketsCount(); i++) {
                newlyCreatedTickets.add(ticketsMapper.toEntity(ticketsCreationEvent));
            }
        }
        
        ticketRepository.saveAll(newlyCreatedTickets);
        List<Long> eventLocationIds =
                ticketsCreationEvents.stream().map(TicketsCreationEvent::getEventLocationId).toList();
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

    public int getAvailableTicketsCount(long locationEventsId) {
        return ticketRepository.countByLocationAndStatus(locationEventsId,
                TicketStatus.AVAILABLE);
    }

    @Transactional
    public List<TicketListItem> getTicketsByOrder(Order order) {
        List<Ticket> tickets = order.getTickets();
        List<TicketListItem> ticketListItems =
                enrichTicketsWithEventDetails(tickets);
        log.info("Tickets for order ID {} has been fetched", order.getId());
        return ticketListItems;
    }

    public void finalizeOrder(long orderId) {
        List<Ticket> tickets = ticketRepository.findAllByOrderId(orderId);
        for (Ticket ticket : tickets) {
            ticket.setStatus(TicketStatus.BOOKED);
        }

        ticketRepository.saveAll(tickets);
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
        String qrBase64 =
                generateQrCodeBase64("ticket:" + ticketListItem.getNumber());
        context.setVariable("qrCode", qrBase64);
        context.setVariable("qrCode", qrBase64);
        String html = templateEngine.process("ticket-template", context);


        return html;
    }

    private String generateQrCodeBase64(String content) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(content,
                    BarcodeFormat.QR_CODE, 200, 200);

            BufferedImage image =
                    MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", outputStream);

            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
        }
        return null;
    }
}