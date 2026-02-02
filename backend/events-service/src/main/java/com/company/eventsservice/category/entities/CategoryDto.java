package com.company.eventsservice.category.entities;

import java.time.Instant;

public record CategoryDto(String name, long id, Instant createdAt, Instant updatedAt) {
}
