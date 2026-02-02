package com.company.eventsservice.category.entities;

import com.company.eventsservice.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest dto);

    CategoryDto toDto(Category entity);
}