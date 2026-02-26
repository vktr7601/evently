package com.evently.notification.infrastructure.kafka.consumers;

import com.evently.notification.notifications.service.NotificationService;
import com.evently.notification.processedEvent.ProcessedEvent;
import com.evently.notification.processedEvent.ProcessedEventRepository;
import constants.KafkaTopics;
import events.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredKafkaConsumer {
    private final NotificationService notificationService;
    private final ProcessedEventRepository processedEventRepository;

    @Transactional
    @KafkaListener(topics = KafkaTopics.USER_REGISTERED)
    public void onUserRegistered(UserRegisteredEvent event) {
        if (processedEventRepository.existsById(event.getMessageId())) {
            log.info("Event {} already processed. Skipping.",
                    event.getMessageId());
            return;
        }
        try {
            if (event.isShouldReceiveNotification()) {
                notificationService.createHelloNotification(event);
            }

            ProcessedEvent processedEvent =
                    processedEventRepository.save(new ProcessedEvent(event.getMessageId(), Instant.now()));

        } catch (DataIntegrityViolationException e) {
            log.warn("Duplicate event detected during save: {}",
                    event.getMessageId());
        }
    }
}