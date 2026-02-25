package com.evently.booking.infrastructure.kafka.consumer;

import com.evently.booking.ticket.service.TicketService;
import constants.KafkaTopics;
import events.event.EventCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventCreatedKafkaConsumer {
    private final TicketService ticketService;

    @KafkaListener(topics = KafkaTopics.EVENT_CREATED)
    public void consumeMessage(EventCreated eventCreated) {
        log.info("Received EVENT_CREATED for {} ticket creation events",
                eventCreated.getTicketsCreationEvents().size());

        ticketService.createTickets(eventCreated);
    }
}