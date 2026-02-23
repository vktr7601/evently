package com.evently.users.processedEvent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
public class ProcessedEvent {
    @Id
    private UUID eventId;

    private Instant processedAt;

//    private String eventType; // Useful for debugging or cleanup later

    public ProcessedEvent() {}

    public ProcessedEvent(UUID eventId, String eventType) {
        this.eventId = eventId;
        this.processedAt = Instant.now();
    }
}