package com.evently.booking.infrastructure.kafka.producer;

import dtos.KafkaTopics;
import dtos.TicketsCreated;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@AllArgsConstructor
public class KafkaMessageProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessages(TicketsCreated ticketsCreated) {
        kafkaTemplate.send(KafkaTopics.TICKETS_CREATED, ticketsCreated);
    }
}