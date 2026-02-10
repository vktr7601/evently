package com.evently.notification.notifications;

import java.time.Instant;

public record NotificationDto(long id, String title, String message, boolean isRead, Instant createdAt) {
}