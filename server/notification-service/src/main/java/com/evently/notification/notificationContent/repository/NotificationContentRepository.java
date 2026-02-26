package com.evently.notification.notificationContent.repository;

import com.evently.notification.notificationContent.model.NotificationContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationContentRepository extends JpaRepository<NotificationContent, Long> {
}