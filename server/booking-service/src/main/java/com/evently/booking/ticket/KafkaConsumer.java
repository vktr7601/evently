package com.evently.booking.ticket;

import dtos.EventCreated;
import dtos.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final TicketService ticketService;

    @KafkaListener(topics = KafkaTopics.EVENT_CREATED)
    public void consumeMessage(EventCreated eventCreated) {
        ticketService.createTickets(eventCreated.getTicketAllocations());
    }
}