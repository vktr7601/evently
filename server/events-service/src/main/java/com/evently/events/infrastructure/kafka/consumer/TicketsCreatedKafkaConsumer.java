package com.evently.events.infrastructure.kafka.consumer;

import com.evently.events.eventsLocations.service.EventsLocationsService;
import constants.KafkaTopics;
import events.ticket.TicketsCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketsCreatedKafkaConsumer {
    private final EventsLocationsService eventsLocationsService;

    @KafkaListener(topics = KafkaTopics.TICKETS_CREATED)
    public void onTicketsCreated(TicketsCreated ticketsCreated) {
        log.info("Received tickets created event: {}", ticketsCreated);
        eventsLocationsService.markAsActive(ticketsCreated);
    }
}