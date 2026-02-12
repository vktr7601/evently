package com.evently.events.eventsCategories.entities;

import com.evently.events.category.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class EventCategoriesDto {
    private long eventId;
    private Category category;

    public EventCategoriesDto(long eventId, Category category) {
        setEventId(eventId);
        setCategory(category);
    }
}