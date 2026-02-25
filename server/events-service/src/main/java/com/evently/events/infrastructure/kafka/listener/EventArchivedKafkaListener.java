package com.evently.events.infrastructure.kafka.listener;

import com.evently.events.infrastructure.kafka.producer.KafkaProducer;
import events.event.EventArchived;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventArchivedKafkaListener {
    private final KafkaProducer kafkaProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onEventArchived(EventArchived eventFinished) {
        var message = new EventArchived();
        eventFinished.setEventLocationId(eventFinished.getEventLocationId());

        kafkaProducer.sendEventFinishedMessage(message);
    }
}