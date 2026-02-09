package com.evently.events.config;

import dtos.EventCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, EventCreated> kafkaTemplate;

    public void send(String topic, EventCreated message) {
        kafkaTemplate.send("event-created", message);
    }
}