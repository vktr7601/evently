package com.evently.events.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {
    boolean existsByName(String name);

    @Query("""
        SELECT cat
        FROM Category cat
        WHERE cat.name IN :names
        """)
    List<Category> findAllByNameIn(@Param("names") List<String> names);


    default String getEntityName() {
        return "Classification";
    }


    @Query("""
        SELECT cat
        FROM Category cat
        WHERE cat.id IN (:ids)
        """)
    List<Category> findAllByIdIn(@Param("ids") List<Long> ids);
}