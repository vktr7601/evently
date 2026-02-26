package com.evently.users.infrastructure.kafka.producer;

import constants.KafkaTopics;
import events.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessages(UserRegisteredEvent userRegisteredEvent) {
        userRegisteredEvent.setMessageId(UUID.randomUUID().toString());
        userRegisteredEvent.setOccurredAt(Instant.now());
        kafkaTemplate.send(KafkaTopics.USER_REGISTERED, userRegisteredEvent);
    }
}