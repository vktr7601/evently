package com.evently.events.config;

import com.evently.events.eventsLocations.EventsLocationsService;
import dtos.EventFinished;
import dtos.KafkaTopics;
import dtos.TicketsCreated;
import events.eventCreated.EventCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EventKafkaListener {

    private final KafkaProducer kafkaProducer;
    private final EventsLocationsService eventsLocationsService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEventCreatedNotification(EventCreated event) {
//        var message = new EventCreated();
//        message.setEventId(event.getEventId());
//        message.setEventName(event.getEventName());
//        message.setArtistId(event.getArtistId());
//        message.setCategories(event.getCategories());
//        message.setTicketsCreationEvents(event.getTicketsCreationEvents());

        kafkaProducer.sendEventCreatedMessage(event);
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEventArchived(EventFinished eventFinished) {
        var message = new EventFinished();
        eventFinished.setEventLocationId(eventFinished.getEventLocationId());

        kafkaProducer.sendEventFinishedMessage(message);
    }

    @KafkaListener(topics = KafkaTopics.TICKETS_CREATED)
    public void handleTicketsCreated(TicketsCreated ticketsCreated) {
        eventsLocationsService.markAsActive(ticketsCreated.getEventLocationIds());
    }
}