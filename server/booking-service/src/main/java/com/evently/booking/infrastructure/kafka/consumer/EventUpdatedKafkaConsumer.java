package com.evently.booking.infrastructure.kafka.consumer;

import com.evently.booking.ticket.service.TicketService;
import constants.KafkaTopics;
import events.event.EventUpdated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventUpdatedKafkaConsumer {
    private final TicketService ticketService;

    @KafkaListener(topics = KafkaTopics.NEW_EVENT_LOCATIONS_ADDED)
    public void onEventUpdated(EventUpdated eventsLocationsDto) {
        ticketService.addTickets(eventsLocationsDto.getNewTicketsToCreate());
    }
}