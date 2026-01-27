package com.company.ticket_service.repository;

import com.company.ticket_service.category.Category;
import com.company.ticket_service.category.CategoryMapper;
import com.company.ticket_service.category.CategoryRepository;
import com.company.ticket_service.category.entities.CategoryDto;
import com.company.ticket_service.category.entities.CategoryRequest;
import com.company.ticket_service.core.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTests {

  @Autowired private CategoryRepository categoryRepository;

  @Autowired private TestEntityManager entityManager;

  private final CategoryMapper mapper = Mappers.getMapper(CategoryMapper.class);
  private Category category;

  @BeforeEach
  public void beforeEach() {
    category = Category.builder().name("Electronics").build();
  }

  @Test
  void recordSuccessfullyCreated_when_saveMethodInvoked() {
    Category saved = categoryRepository.save(category);

    assertNotNull(saved.getId(), "Saved Category should not be null");
    assertEquals("Electronics", saved.getName(), "Saved Category should be equal to");
    assertNotNull(saved.getCreatedAt(), "Saved Category  created at should not be null");
    assertNotNull(saved.getUpdatedAt(), "Saved Category  updated at should not be null");
  }

  @Test
  void findByIdOrThrowReturnCategory_when_categoryExists() {
    entityManager.persistAndFlush(category);

    Category found = categoryRepository.findByIdOrThrow(category.getId());

    assertNotNull(found, "Category is expected to be found, but it was not");
    assertEquals(category.getId(), found.getId(), "Id should be equal to");
  }

  @Test
  void findByIdOrThrowThrowException_when_categoryDoesNotExist() {
    assertThrows(ResourceNotFoundException.class, () -> categoryRepository.findByIdOrThrow(999L));
  }

  @Test
  void deleteByIdRemovesCategory_when_categoryExists() {
    entityManager.persistAndFlush(category);
    long id = category.getId();

    categoryRepository.deleteById(id);
    entityManager.flush();

    assertThrows(
        ResourceNotFoundException.class,
        () -> categoryRepository.findByIdOrThrow(id),
        "Category with id " + id + " should not exists, but it was.");
  }

  @Test
  void toEntityReturnsCategory_when_requestProvided() {
    CategoryRequest request = CategoryRequest.builder().name("Electronics").build();

    Category entity = mapper.toEntity(request);

    assertNotNull(entity, "Category is expected to be found, but it was not");
    assertEquals(request.getName(), entity.getName(), "Name should be equal to");
  }

  @Test
  void toEntityReturnsNull_when_requestIsNull() {
    Category entity = mapper.toEntity(null);

    assertNull(entity, "Entity is expected to be null, but it was not");
  }

  @Test
  void toDtoReturnsCategoryDto_when_entityProvided() {
    entityManager.persistAndFlush(category);

    CategoryDto dto = mapper.toDto(category);

    assertNotNull(dto, "Category Dto is expected to be not null, but it was");
    assertEquals(category.getId(), dto.id(), "Id should be equal to");
    assertEquals(category.getName(), dto.name(), "Name should be equal to");
    assertEquals(category.getCreatedAt(), dto.createdAt(), "Created Date should be equal to");
    assertEquals(category.getUpdatedAt(), dto.updatedAt(), "Updated Date should be equal to");
  }

  @Test
  void toDtoReturnsNull_when_entityIsNull() {
    CategoryDto dto = mapper.toDto(null);

    assertNull(dto, "Category Dto is expected to be null, but it was not null.");
  }
}