package com.evently.events.event.entities;

import com.evently.events.category.entities.CategoryDto;

import java.util.List;

public record EventDto(
        String name,
        List<CategoryDto> categories,
        String performer,
        long id
) {
}