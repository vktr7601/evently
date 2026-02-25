package com.evently.notification.notifications.service;

import com.evently.notification.infrastructure.clients.UserServiceClient;
import com.evently.notification.notificationContent.NotificationContent;
import com.evently.notification.notificationContent.NotificationContentRepository;
import com.evently.notification.notifications.dto.NotificationListItemDto;
import com.evently.notification.notifications.model.Notification;
import com.evently.notification.notifications.repository.NotificationRepository;
import events.event.EventLive;
import events.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationContentRepository notificationContentRepository;
    private final UserServiceClient userServiceClient;

    public void saveAll(List<Notification> notificationList) {
        notificationRepository.saveAll(notificationList);
    }

    public List<NotificationListItemDto> findAllByUserId(long userId) {
        List<NotificationListItemDto> allByUserId =
                notificationRepository.findAllByUserId(userId);
        log.info("Find all notifications by userId: {}", allByUserId);

        return allByUserId;
    }

    public void markNotificationAsRead(long notificationId) {
        log.info("mark notification as read for notificationId={}",
                notificationId);
        notificationRepository.markNotificationAsRead(notificationId);
        log.info("mark notification as read for notificationId={}",
                notificationId);
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

    @Transactional
    public void createNotification(EventLive eventAlive) {
        NotificationContent notificationContent = new NotificationContent();
        notificationContent.setTitle("Event created: $s".formatted(eventAlive.getEventName()));
        notificationContent.setHtmlBody("Some cool message");
        List<Long> userIds =
                userServiceClient.fetchUserIds(eventAlive.getCategoryIds());

        notificationContentRepository.save(notificationContent);

        List<Notification> notifications = new ArrayList<>();
        for (Long userId : userIds) {
            Notification notification = new Notification();
            notification.setContent(notificationContent);
            notification.setUserId(userId);
            notifications.add(notification);
        }

        saveAll(notifications);

    }

    @Transactional
    public void createNotification(UserRegisteredEvent userRegisteredEvent) {

        NotificationContent notificationContent = new NotificationContent();
        notificationContent.setTitle("Welcome on board: %s".formatted(userRegisteredEvent.getFirstName() + " " + userRegisteredEvent.getLastName()));
        notificationContent.setHtmlBody("Welcome on board");

        notificationContentRepository.save(notificationContent);
        List<Notification> notifications = new ArrayList<>();
        Notification notification = new Notification();
        notification.setContent(notificationContent);
        notification.setUserId(userRegisteredEvent.getUserId());
        notifications.add(notification);

        saveAll(notifications);


    }
}