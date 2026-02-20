package com.evently.events.category;

import com.evently.events.category.entities.CategoryMapper;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.category.entities.CategoryRequest;
import exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDto create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName()))
            throw new DuplicateResourceException("Category already exists");

        Category category = categoryMapper.toEntity(request);

        Category createdEntity = categoryRepository.save(category);

        return categoryMapper.toDto(createdEntity);
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    public List<Category> findAllByNameIn(List<String> names) {
        if (names == null || names.isEmpty()) {
            return Collections.emptyList();
        }


        List<Category> existingCategories = categoryRepository.findAllByNameIn(names);

        if (existingCategories.isEmpty() || names.size() != existingCategories.size()) {
            throw new ResolutionException("Invalid request");
        }

        return existingCategories;
    }

    public List<Category> findAllByIdIn(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        List<Category> existingCategories = categoryRepository.findAllByIdIn(ids);

        if (existingCategories.isEmpty() || ids.size() != existingCategories.size()) {
            throw new ResolutionException("Invalid request");
        }

        return existingCategories;
    }
}