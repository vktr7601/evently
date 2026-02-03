package com.evently.events.category;

import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {
    boolean existsByName(String name);

    List<Category> findAllByNameIn(List<String> names);

    default String getEntityName() {
        return "Classification";
    }
}