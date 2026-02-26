package com.evently.notification.infrastructure.kafka.subscribers;

import com.evently.notification.notifications.service.NotificationService;
import com.evently.notification.processedEvent.ProcessedEventRepository;
import events.event.EventLive;
import constants.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventLiveKafkaConsumer {
    private final NotificationService notificationService;
    private final ProcessedEventRepository processedEventRepository;

    @KafkaListener(topics = KafkaTopics.EVENT_LIVE)
    public void onEventLive(EventLive eventLive) {
        notificationService.createHelloNotification(eventLive);
    }
}