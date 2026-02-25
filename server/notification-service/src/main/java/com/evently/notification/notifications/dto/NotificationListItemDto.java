package com.evently.notification.notifications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class NotificationListItemDto implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("htmlBody")
    private String htmlBody;
    @JsonProperty("isRead")
    private boolean isRead;
    @JsonProperty("createdAt")
    private Instant createdAt;
}