package com.evently.events.category;

import com.evently.events.category.entities.CategoryDto;
import com.evently.events.category.entities.CategoryMapper;
import com.evently.events.category.entities.CategoryRequest;
import exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }
}