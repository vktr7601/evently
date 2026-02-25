package com.evently.notification.processedEvent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedEvent {
    @Id
    private UUID eventId;
    private Instant processedAt;
}