package com.evently.notification.notificationContent.contract;

import com.evently.notification.notificationContent.model.NotificationContent;

public interface NotificationContentProvider<T> {
    NotificationContent generateNotificationContent(T event);
}