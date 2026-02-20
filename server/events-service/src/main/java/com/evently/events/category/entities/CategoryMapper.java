package com.evently.events.category.entities;

import com.evently.events.category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryRequest dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    public CategoryDto toDto(Category entity) {
        return new CategoryDto(entity.getId(), entity.getName());
    }
}