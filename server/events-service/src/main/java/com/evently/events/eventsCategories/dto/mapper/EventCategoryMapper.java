package com.evently.events.eventsCategories.dto.mapper;

import com.evently.events.category.model.Category;
import com.evently.events.category.dto.CategoryDto;
import com.evently.events.event.model.Event;
import com.evently.events.eventsCategories.dto.EventCategoriesDto;
import com.evently.events.eventsCategories.model.EventsCategories;
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