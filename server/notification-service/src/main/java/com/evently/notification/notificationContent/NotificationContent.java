package com.evently.notification.notificationContent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "notification_content")
public class NotificationContent extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "html_body")
    private String htmlBody;
}