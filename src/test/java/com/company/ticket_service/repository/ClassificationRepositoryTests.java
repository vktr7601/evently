package com.company.ticket_service.repository;

import com.company.ticket_service.classification.Classification;
import com.company.ticket_service.classification.ClassificationRepository;
import com.company.ticket_service.classification.entities.ClassificationDto;
import com.company.ticket_service.classification.entities.ClassificationRequest;
import com.company.ticket_service.classification.mapper.ClassificationMapper;
import com.company.ticket_service.core.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClassificationRepositoryTests {

    private final ClassificationMapper mapper = Mappers.getMapper(ClassificationMapper.class);
    @Autowired
    private ClassificationRepository classificationRepository;
    @Autowired
    private TestEntityManager entityManager;
    private Classification category;

    @BeforeEach
    public void beforeEach() {
        category = Classification.builder().name("Electronics").build();
    }

    @Test
    void recordSuccessfullyCreated_when_saveMethodInvoked() {
        Classification saved = classificationRepository.save(category);

        assertNotNull(saved.getId(), "Saved Category should not be null");
        assertEquals("Electronics", saved.getName(), "Saved Category should be equal to");
        assertNotNull(saved.getCreatedAt(), "Saved Category  created at should not be null");
        assertNotNull(saved.getUpdatedAt(), "Saved Category  updated at should not be null");
    }

    @Test
    void findByIdOrThrowReturnCategory_when_categoryExists() {
        entityManager.persistAndFlush(category);

        Classification found = classificationRepository.findByIdOrThrow(category.getId());

        assertNotNull(found, "Category is expected to be found, but it was not");
        assertEquals(category.getId(), found.getId(), "Id should be equal to");
    }

    @Test
    void findByIdOrThrowThrowException_when_categoryDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> classificationRepository.findByIdOrThrow(999L));
    }

    @Test
    void deleteByIdRemovesCategory_when_categoryExists() {
        entityManager.persistAndFlush(category);
        long id = category.getId();

        classificationRepository.deleteById(id);
        entityManager.flush();

        assertThrows(
                ResourceNotFoundException.class,
                () -> classificationRepository.findByIdOrThrow(id),
                "Category with id " + id + " should not exists, but it was.");
    }

    @Test
    void toEntityReturnsCategory_when_requestProvided() {
        ClassificationRequest request = new ClassificationRequest("Electronics");

        Classification entity = mapper.toEntity(request);

        assertNotNull(entity, "Category is expected to be found, but it was not");
        assertEquals(request.name(), entity.getName(), "Name should be equal to");
    }

    @Test
    void toEntityReturnsNull_when_requestIsNull() {
        Classification entity = mapper.toEntity(null);

        assertNull(entity, "Entity is expected to be null, but it was not");
    }

    @Test
    void toDtoReturnsCategoryDto_when_entityProvided() {
        entityManager.persistAndFlush(category);

        ClassificationDto dto = mapper.toDto(category);

        assertNotNull(dto, "Category Dto is expected to be not null, but it was");
        assertEquals(category.getId(), dto.id(), "Id should be equal to");
        assertEquals(category.getName(), dto.name(), "Name should be equal to");
        assertEquals(category.getCreatedAt(), dto.createdAt(), "Created Date should be equal to");
        assertEquals(category.getUpdatedAt(), dto.updatedAt(), "Updated Date should be equal to");
    }

    @Test
    void toDtoReturnsNull_when_entityIsNull() {
        ClassificationDto dto = mapper.toDto(null);

        assertNull(dto, "Category Dto is expected to be null, but it was not null.");
    }
}
