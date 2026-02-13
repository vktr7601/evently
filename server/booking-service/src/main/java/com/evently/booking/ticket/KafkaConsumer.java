package com.evently.booking.ticket;

import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

//    @KafkaListener(topics = KafkaTopics.EVENT_CREATED)
//    public void consumeMessage( userRegisteredTopic) {
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