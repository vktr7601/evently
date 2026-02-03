package com.evently.notification.notifications;

import jakarta.persistence.Entity;
import utils.BaseEntity;

import java.time.LocalDateTime;

@Entity
public class Notification extends BaseEntity {
    private Long userId;
    private String title;
    private String content;
    private boolean isRead = false;
    private LocalDateTime createdAt;
}