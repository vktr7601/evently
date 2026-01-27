package com.company.ticket_service.category.entities;

import java.time.Instant;

public record CategoryDto(String name, long id, Instant createdAt, Instant updatedAt) {}