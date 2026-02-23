package com.evently.booking.infrastructure.kafka.consumer;

import com.evently.booking.ticket.TicketService;
import dtos.KafkaTopics;
import events.eventCreated.EventTicketsBulkUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventTicketsUpdateKafkaConsumer {

    private final TicketService ticketService;

    @KafkaListener(topics = KafkaTopics.EVENT_TICKETS_BULK_UPDATE)
    public void consumeBulkUpdate(EventTicketsBulkUpdate event) {
        log.info("Received bulk update for event ID: {} with {} location updates",
                event.getEventId(), event.getUpdates().size());
        ticketService.processBulkUpdate(event);
    }
}