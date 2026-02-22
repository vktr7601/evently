package com.evently.booking.infrastructure.kafka.consumer;

import com.evently.booking.ticket.TicketService;
import dtos.EventCancelled;
import dtos.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventCancelledKafkaConsumer {
    private final TicketService ticketService;

    @KafkaListener(topics = KafkaTopics.EVENT_CANCELLED)
    public void consumeMessage(EventCancelled eventCancelled) {
        log.info("Received EVENT_CREATED for {} ticket creation events");
        ticketService.cancelTicketsForEvents(eventCancelled.getEventsLocationsIds());
    }
}