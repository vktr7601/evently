package com.evently.notification.notifications;

import com.evently.notification.notificationContent.NotificationContent;
import com.evently.notification.notificationContent.NotificationContentRepository;
import dtos.EventCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;
    private final NotificationContentRepository notificationContentRepository;
    private final UserPreferencesClient userPreferencesClient;

    @KafkaListener(topics = "event-created")
    public void consumeMessage(EventCreated message) {
        log.info("Received Kafka message: {}", message);
        List<Long> userIds = userPreferencesClient.fetchUserIds(message.getCategories());
        String template = generateSimpleHtml(message);
        NotificationContent notificationContent = new NotificationContent();
        notificationContent.setHtmlBody(template);
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

    public String generateSimpleHtml(EventCreated message) {
        return """
            <html>
                <body style="font-family: Arial, sans-serif; color: #333; line-height: 1.6;">
                    <h2 style="color: #4A90E2;">New Event: %s</h2>
                    <p>Hello! A new event has been posted in a category you follow.</p>
                    <p>Exciting things are happening! For more details regarding the location,
                       timing, and special guests, please follow the link below:</p>
                    <div style="margin-top: 20px;">
                        <a href="http://evently.com/events/%s"
                           style="background: #28a745; color: white; padding: 12px 20px; text-decoration: none; border-radius: 5px; font-weight: bold;">
                           View Full Event Details
                        </a>
                    </div>
                    <p style="margin-top: 30px; font-size: 0.9em; color: #777;">
                    </p>
                </body>
            </html>
            """.formatted(
            message.getEventName(),
            message.getEventId()
//            String.join(", ", message.getCategories())
        );
    }
}