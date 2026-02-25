package com.evently.notification.infrastructure.kafka.consumers;

import com.evently.notification.infrastructure.clients.UserServiceClient;
import com.evently.notification.notificationContent.NotificationContent;
import com.evently.notification.notificationContent.NotificationContentRepository;
import com.evently.notification.notifications.model.Notification;
import com.evently.notification.notifications.service.NotificationService;
import constants.KafkaTopics;
import events.event.EventCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventCreatedKafkaConsumer {

    private final NotificationService notificationService;
    private final NotificationContentRepository notificationContentRepository;
    private final UserServiceClient userServiceClient;

    @KafkaListener(topics = KafkaTopics.EVENT_CREATED)
    public void onEventCreated(EventCreated message) {
        List<Long> userIds =
                userServiceClient.fetchUserIds(message.getCategories());
        NotificationContent notificationContent = new NotificationContent();
        notificationContent.setHtmlBody("HELLO");
        notificationContent.setTitle("New event created: " + message.getEventName());

        notificationContentRepository.save(notificationContent);

        List<Notification> notifications = new ArrayList<>();
        for (Long userId : userIds) {
            Notification notification = new Notification();
            notification.setContent(notificationContent);
            notification.setUserId(userId);
            notifications.add(notification);
        }

        notificationService.saveAll(notifications);
    }
}