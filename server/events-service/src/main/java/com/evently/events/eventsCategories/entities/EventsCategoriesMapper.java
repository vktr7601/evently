package com.evently.events.eventsCategories.entities;

import com.evently.events.category.Category;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.event.Event;
import com.evently.events.eventsCategories.EventsCategories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventsCategoriesMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    EventsCategories toEventsCategories(Event event, Category category);

    @Mapping(source = "category.id", target = "id")
    @Mapping(source = "category.name", target = "name")
    CategoryDto toCategoryDto(EventCategoriesDto eventCategoriesDto);

    CategoryDto toCategoryDto(Category categoryDto);
}