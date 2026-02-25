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

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredKafkaConsumer {
    private final NotificationService notificationService;
    private final ProcessedEventRepository processedEventRepository;

    @Transactional
    @KafkaListener(topics = KafkaTopics.USER_REGISTERED)
    public void onUserRegistered(UserRegisteredEvent event) {
        if (processedEventRepository.existsById(event.getUuid())) {
            log.info("Event {} already processed. Skipping.", event.getUuid());
            return;
        }
        try {
            notificationService.createNotification(event);

            ProcessedEvent processedEvent =
                    processedEventRepository.save(new ProcessedEvent(event.getUuid()));

        } catch (DataIntegrityViolationException e) {
            // 4. Handle race conditions (If two threads process the same ID
            // at once)
            log.warn("Duplicate event detected during save: {}",
                    event.getUuid());
        }
    }
}