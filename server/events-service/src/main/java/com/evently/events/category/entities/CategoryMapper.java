package com.evently.events.category.entities;

import com.evently.events.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eventsCategories", ignore = true)
    Category toEntity(CategoryRequest dto);

    CategoryDto toDto(Category entity);
}