package com.company.ticket_service.category;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {
  boolean existsByName(String name);

  List<Category> findAllByNameIn(List<String> names);

  default String getEntityName() {
    return "Category";
  }
}