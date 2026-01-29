package com.company.ticket_service.classification;

import com.company.ticket_service.classification.entities.ClassificationDto;
import com.company.ticket_service.classification.entities.ClassificationRequest;
import com.company.ticket_service.classification.mapper.ClassificationMapper;
import com.company.ticket_service.core.exceptions.DuplicateResourceException;
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
