package com.evently.notification.notifications;

import com.evently.notification.notificationContent.NotificationContent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification extends BaseEntity {
    @Column(name = "userId")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private NotificationContent content;
    @Column(name = "isRead")
    private boolean isRead;

    @Override
    public void onCreate() {
        super.onCreate();
        isRead = false;
    }
}