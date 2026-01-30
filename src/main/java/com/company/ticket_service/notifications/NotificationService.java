package com.company.ticket_service.notifications;

import com.company.ticket_service.notifications.entities.NotificationDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "event_created", groupId = "sample-consumer-group")
    public void createNotification() {

    }

    public List<NotificationDto> getNotificationsByEmail(String email) {
        return notificationRepository.findAllByAccountEmail(email);
    }
}
