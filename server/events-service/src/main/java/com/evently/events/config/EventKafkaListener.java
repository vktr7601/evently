package com.evently.events.config;

import com.evently.events.infrastructure.kafka.producer.KafkaProducer;
import events.event.EventLive;
import events.event.EventTicketsBulkUpdate;
import events.event.EventUpdated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventKafkaListener {

    private final KafkaProducer kafkaProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEventArchived(EventUpdated eventFinished) {
        kafkaProducer.sendNewLocationsAdded(eventFinished);
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEventArchived(EventTicketsBulkUpdate eventFinished) {
        kafkaProducer.sendEventsTicketBulkUpdate(eventFinished);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEventLive(EventLive event) {
        kafkaProducer.sendEventLive(event);
    }
}