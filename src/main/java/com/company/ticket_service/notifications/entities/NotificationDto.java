package com.company.ticket_service.notifications.entities;

public record NotificationDto(
        String content,
        boolean isRead
) {
}
