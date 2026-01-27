package com.company.ticket_service.category;

import com.company.ticket_service.category.entities.CategoryDto;
import com.company.ticket_service.category.entities.CategoryRequest;
import com.company.ticket_service.core.exceptions.DuplicateResourceException;
import com.company.ticket_service.core.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Transactional
  public CategoryDto create(CategoryRequest request) {
    if (categoryRepository.existsByName(request.name()))
      throw new DuplicateResourceException("Category already exists");

    Category category = categoryMapper.toEntity(request);

    Category createdEntity = categoryRepository.save(category);

    return categoryMapper.toDto(createdEntity);
  }

  @Cacheable(value = "categories", key = "'id:' + #id")
  public CategoryDto findById(long id) {
    return categoryRepository
        .findById(id)
        .map(categoryMapper::toDto)
        .orElseThrow(() -> new ResourceNotFoundException("Category with ID: %s ".formatted(id)));
  }
}