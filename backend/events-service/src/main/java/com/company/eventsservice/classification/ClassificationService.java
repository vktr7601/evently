package com.company.eventsservice.classification;

import com.company.eventsservice.classification.entities.ClassificationDto;
import com.company.eventsservice.classification.entities.ClassificationMapper;
import com.company.eventsservice.classification.entities.ClassificationRequest;
import exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
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
}
