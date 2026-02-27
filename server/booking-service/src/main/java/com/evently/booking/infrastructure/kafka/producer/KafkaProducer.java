package com.evently.booking.infrastructure.kafka.producer;

import constants.KafkaTopics;
import events.ticket.TicketsCreated;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTicketCreated(TicketsCreated ticketsCreated) {
        kafkaTemplate.send(KafkaTopics.TICKETS_CREATED, ticketsCreated);
    }
}