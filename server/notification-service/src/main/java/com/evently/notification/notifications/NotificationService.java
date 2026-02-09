package com.evently.notification.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void saveAll(List<Notification> notificationList) {
        notificationRepository.saveAll(notificationList);
    }
}