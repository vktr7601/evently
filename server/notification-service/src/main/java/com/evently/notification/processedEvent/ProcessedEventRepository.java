package com.evently.notification.processedEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessedEventRepository extends JpaRepository<com.evently.notification.processedEvent.ProcessedEvent, UUID> {
}