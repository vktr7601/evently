package com.evently.notification.notificationContent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "notification_content")
public class NotificationContent extends BaseEntity {
    @Column(name = "title", length = 2048)
    private String title;
    @Column(name = "html_body", length = 4048)
    private String htmlBody;
}