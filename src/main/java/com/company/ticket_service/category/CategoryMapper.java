package com.company.ticket_service.category;

import com.company.ticket_service.category.entities.CategoryDto;
import com.company.ticket_service.category.entities.CategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  Category toEntity(CategoryRequest dto);

  CategoryDto toDto(Category entity);
}