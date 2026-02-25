package com.evently.events.infrastructure.kafka.listener;

import com.evently.events.infrastructure.kafka.producer.KafkaProducer;
import events.event.EventCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventCreatedKafkaListener {
    private final KafkaProducer kafkaProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onEventCreated(EventCreated event) {
        log.info("Transaction committed — sending EventCreated to Kafka for " +
                        "eventId: {}",
                event.getEventId());
//        try {
//            event.setMessageId(UUID.randomUUID().toString());
//            event.setOccurredAt(Instant.now());
//            kafkaProducer.sendEventCreated(event);
//        } catch (Exception e) {
//            log.error("Failed to publish EventCreated for eventId: {}",
//                    event.getEventId(), e);
//        }
    }
}