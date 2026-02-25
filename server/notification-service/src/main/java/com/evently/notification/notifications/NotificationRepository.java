package com.evently.notification.notifications;

import com.evently.notification.notifications.entities.NotificationListItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,
        Long> {
    @Query("""
            SELECT new com.evently.notification.notifications.entities.NotificationListItemDto(
                n.id,
                nc.title,
                nc.htmlBody,
                n.isRead,
                n.createdAt
            )
            FROM Notification n
            INNER JOIN NotificationContent nc ON n.content.id = nc.id
            WHERE n.userId = :userId
              AND n.isDeleted = false
            ORDER BY n.createdAt DESC
            """)
    List<NotificationListItemDto> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE Notification  as n
            SET n.isRead = true
            WHERE n.id = :notificationId
            """)
    void markNotificationAsRead(@Param("notificationId") Long notificationId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE Notification  as n
            SET n.isDeleted = true
            WHERE n.id = :notificationId
            """)
    void deleteNotificationById(Long id);
}