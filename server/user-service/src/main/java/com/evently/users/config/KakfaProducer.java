package com.evently.users.config;

import dtos.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static dtos.KafkaTopics.USER_REGISTERED;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakfaProducer {
    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public void sendUserRegisteredEvent(UserRegisteredEvent event) {
        log.info("Sending user registered event to Kafka Producer");
        kafkaTemplate.send(USER_REGISTERED, event);
    }
}