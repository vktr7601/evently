package com.evently.notification.notifications.model;

import com.evently.notification.notificationContent.NotificationContent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private NotificationContent content;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Override
    public void onCreate() {
        super.onCreate();
        isRead = false;
        isDeleted = false;
    }
}