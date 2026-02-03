package com.evently.events.category.entities;

import java.time.Instant;

public record CategoryDto(String name, long id, Instant createdAt, Instant updatedAt) {
}
