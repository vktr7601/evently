package com.evently.events.config;

import dtos.EventCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static dtos.KafkaTopics.EVENT_CREATED;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, EventCreated> kafkaTemplate;

    public void sendEventCreatedMessage(EventCreated message) {
        kafkaTemplate.send(EVENT_CREATED, message);
    }
}