package com.evently.notification.notifications.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter(AccessLevel.PRIVATE)
public class NotificationListItemDto {
    private long id;
    private String title;
    private String message;
    private boolean isRead;
    private Instant createdAt;

    public NotificationListItemDto(long id, String title, String message, boolean isRead, Instant createdAt) {
        setId(id);
        setTitle(title);
        setMessage(message);
        setRead(isRead);
        setCreatedAt(createdAt);
    }
}