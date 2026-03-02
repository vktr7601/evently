package com.evently.events.category.dto.mapper;

import com.evently.events.category.dto.CategoryDto;
import com.evently.events.category.dto.CategorySeed;
import com.evently.events.category.dto.request.CreateCategoryRequest;
import com.evently.events.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CreateCategoryRequest dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    public CategoryDto toDto(Category entity) {
        return new CategoryDto(entity.getId(), entity.getName());
    }

    public Category toEntity(CategorySeed seed) {
        Category category = new Category();
        category.setName(seed.getName());
        return category;
    }
}