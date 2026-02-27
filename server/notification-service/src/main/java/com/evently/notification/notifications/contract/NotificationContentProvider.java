package com.evently.notification.notifications.contract;

import com.evently.notification.notificationContent.model.NotificationContent;

public interface NotificationContentProvider<T> {
    NotificationContent generateNotificationContent(T event);
}