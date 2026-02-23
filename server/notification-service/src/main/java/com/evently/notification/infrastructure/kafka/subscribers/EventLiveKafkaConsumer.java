package com.evently.notification.infrastructure.kafka.subscribers;

import com.evently.notification.notifications.NotificationService;
import com.evently.notification.processedEvent.ProcessedEvent;
import com.evently.notification.processedEvent.ProcessedEventRepository;
import dtos.EventLive;
import dtos.KafkaTopics;
import dtos.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventLiveKafkaConsumer {
    private final NotificationService notificationService;
    private final ProcessedEventRepository processedEventRepository;

    @KafkaListener(topics = KafkaTopics.EVENT_LIVE)
    public void consumeMessage(EventLive eventLive) {
        notificationService.createNotification(eventLive);
    }

    @KafkaListener(topics = KafkaTopics.USER_REGISTERED)
    @Transactional // Ensures the event log and notification logic stay in sync
    public void consumeMessage(UserRegisteredEvent event) {
        // 1. Check if we've already seen this UUID
        if (processedEventRepository.existsById(event.getUuid())) {
            log.info("Event {} already processed. Skipping.", event.getUuid());
            return;
        }

        try {
            // 2. Execute your business logic
            notificationService.createNotification(event);

            // 3. Mark as processed (Save the UUID to your deduplication table)
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