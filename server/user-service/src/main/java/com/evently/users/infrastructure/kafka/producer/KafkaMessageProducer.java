package com.evently.users.infrastructure.kafka.producer;

import constants.KafkaTopics;
import events.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessages(UserRegisteredEvent userRegisteredEvent) {
        kafkaTemplate.send(KafkaTopics.USER_REGISTERED, userRegisteredEvent);
    }
}