package com.company.eventsservice.category;

import com.company.eventsservice.category.entities.CategoryDto;
import com.company.eventsservice.category.entities.CategoryMapper;
import com.company.eventsservice.category.entities.CategoryRequest;
import exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository classificationRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDto create(CategoryRequest request) {
        if (classificationRepository.existsByName(request.name()))
            throw new DuplicateResourceException("Category already exists");

        Category category = categoryMapper.toEntity(request);

        Category createdEntity = classificationRepository.save(category);

        return categoryMapper.toDto(createdEntity);
    }
}
