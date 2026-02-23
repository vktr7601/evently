package com.evently.booking.infrastructure.kafka.consumer;

import com.evently.booking.ticket.TicketService;
import dtos.KafkaTopics;
import events.eventCreated.NewLocationsAdded;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewLocationsAddedKafkaConsumer {
    private final TicketService ticketService;

    @KafkaListener(topics = KafkaTopics.NEW_EVENT_LOCATIONS_ADDED)
    public void consumeEventLocationCancellationMessage(NewLocationsAdded eventsLocationsDto) {
        ticketService.createTickets(eventsLocationsDto.getNewTicketsToCreate());

//        log.info("Event cancelled with result {}", result);
    }
}