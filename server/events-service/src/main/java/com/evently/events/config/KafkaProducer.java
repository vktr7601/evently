package com.evently.events.config;

import dtos.EventCancelled;
import dtos.EventFinished;
import events.eventCreated.EventCreated;
import events.eventCreated.NewLocationsAdded;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static dtos.KafkaTopics.*;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEventCreatedMessage(EventCreated message) {
        kafkaTemplate.send(EVENT_CREATED, message);
    }

    public void sendEventCancellatedMessage(EventCancelled message) {
        kafkaTemplate.send(EVENT_CANCELLED, message);
    }

    public void sendEventFinishedMessage(EventFinished message) {
        kafkaTemplate.send(EVENT_FINISHED, message);
    }

    public void sendNewLocationsAdded(NewLocationsAdded message) {
        kafkaTemplate.send(NEW_EVENT_LOCATIONS_ADDED, message);
    }
}