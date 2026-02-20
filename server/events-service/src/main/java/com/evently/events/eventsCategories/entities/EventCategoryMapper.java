package com.evently.events.eventsCategories.entities;

import com.evently.events.category.Category;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.event.Event;
import com.evently.events.eventsCategories.EventsCategories;
import org.springframework.stereotype.Component;

@Component
public class EventCategoryMapper {
    public EventsCategories toEventsCategories(Event event, Category category) {
        if (event == null && category == null) {
            return null;
        }

        EventsCategories eventsCategories = new EventsCategories();

        eventsCategories.setEvent(event);
        eventsCategories.setCategory(category);
        return eventsCategories;
    }

    public CategoryDto toCategoryDto(EventCategoriesDto eventCategoriesDto) {
        if (eventCategoriesDto == null || eventCategoriesDto.getCategory() == null) {
            return null;
        }

        Category category = eventCategoriesDto.getCategory();

        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public CategoryDto toCategoryDto(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}