package com.evently.events.category.service;

import com.evently.events.category.dto.CategoryDto;
import com.evently.events.category.dto.mapper.CategoryMapper;
import com.evently.events.category.dto.request.CreateCategoryRequest;
import com.evently.events.category.model.Category;
import com.evently.events.category.repository.CategoryRepository;
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
    public CategoryDto create(CreateCategoryRequest request) {
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


        List<Category> existingCategories =
                categoryRepository.findAllByNameIn(names);

        if (existingCategories.isEmpty() || names.size() != existingCategories.size()) {
            throw new ResolutionException("Invalid request");
        }

        return existingCategories;
    }

    public List<Category> findAllByIdIn(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        List<Category> existingCategories =
                categoryRepository.findAllByIdIn(ids);

        if (existingCategories.isEmpty() || ids.size() != existingCategories.size()) {
            throw new ResolutionException("Invalid request");
        }

        return existingCategories;
    }
}