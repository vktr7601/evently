package com.company.ticket_service.classification;

import com.company.ticket_service.classification.entities.ClassificationDto;
import com.company.ticket_service.classification.entities.ClassificationRequest;
import com.company.ticket_service.core.exceptions.DuplicateResourceException;
import com.company.ticket_service.core.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClassificationService {
  private final ClassificationRepository classificationRepository;
  private final ClassificationMapper classificationMapper;

  @Transactional
  public ClassificationDto create(ClassificationRequest request) {
    if (classificationRepository.existsByName(request.name()))
      throw new DuplicateResourceException("Category already exists");

    Classification category = classificationMapper.toEntity(request);

    Classification createdEntity = classificationRepository.save(category);

    return classificationMapper.toDto(createdEntity);
  }

  @Cacheable(value = "categories", key = "'id:' + #id")
  public ClassificationDto findById(long id) {
    return classificationRepository
        .findById(id)
        .map(classificationMapper::toDto)
        .orElseThrow(() -> new ResourceNotFoundException("Category with ID: %s ".formatted(id)));
  }
}