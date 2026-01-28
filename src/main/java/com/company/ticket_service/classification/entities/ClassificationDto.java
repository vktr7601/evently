package com.company.ticket_service.classification.entities;

import java.time.Instant;

public record ClassificationDto(String name, long id, Instant createdAt, Instant updatedAt) {}