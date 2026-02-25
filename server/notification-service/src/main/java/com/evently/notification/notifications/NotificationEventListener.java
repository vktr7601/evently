package com.evently.notification.notifications;

import com.evently.notification.notificationContent.NotificationContent;
import com.evently.notification.notificationContent.NotificationContentRepository;
import events.event.EventCreated;
import constants.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final NotificationService notificationService;
    private final NotificationContentRepository notificationContentRepository;
    private final UserServiceClient userServiceClient;

    @KafkaListener(topics = KafkaTopics.EVENT_CREATED)
    public void consumeMessage(EventCreated message) {
        List<Long> userIds = userServiceClient.fetchUserIds(message.getCategories());
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


//    @KafkaListener(topics = KafkaTopics.USER_REGISTERED)
//    public void consumeMessage(UserRegisteredEvent userRegisteredTopic) {
//        String htmlBody = String.format(
//            "Welcome to the community, %s!" +
//                "<p>Hi %s %s, we're thrilled to have you at <strong>Evently</strong>.</p>" +
//                "<p>Start exploring local events, following your favorite performers, and booking venues today.</p>" +
//                "<br/>" +
//                "<p>Best regards,<br/>The Evently Team</p>",
//            userRegisteredTopic.firstName(), userRegisteredTopic.firstName(), userRegisteredTopic.lastName()
//        );
//        log.info("Received Kafka message: {}", userRegisteredTopic);
//        NotificationContent notificationContent = new NotificationContent();
//        notificationContent.setTitle("Welcome to Evently!");
//        notificationContent.setHtmlBody(htmlBody);
//        notificationContentRepository.save(notificationContent);
//
//        Notification notification = new Notification();
//        notification.setUserId(userRegisteredTopic.userId());
//        notification.setContent(notificationContent);
//        notificationService.save(notification);
//
//    }
}