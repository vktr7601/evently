package com.evently.notification.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void saveAll(List<Notification> notificationList) {
        notificationRepository.saveAll(notificationList);
    }

    public List<NotificationDto> getAllUserNotifications(long userId) {
        return notificationRepository.findNotificationsByUserId(userId);
    }

    public void markNotificationAsRead(long notificationId) {
        log.info("mark notification as read for notificationId={}", notificationId);
        notificationRepository.markNotificationAsRead(notificationId);
        log.info("mark notification as read for notificationId={}", notificationId);
    }

    public void save(Notification notification) {
        log.info("Saving notification {}", notification);
        notificationRepository.save(notification);
        log.info("Saved notification {}", notification);
    }

    public void deleteNotification(long notificationId) {
        log.info("Deleting notification {}", notificationId);
        notificationRepository.deleteNotificationById(notificationId);
        log.info("Deleted notification {}", notificationId);
    }
}