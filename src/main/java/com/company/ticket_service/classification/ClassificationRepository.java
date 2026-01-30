package com.company.ticket_service.classification;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassificationRepository extends BaseRepository<Classification> {
    boolean existsByName(String name);

    List<Classification> findAllByNameIn(List<String> names);

    default String getEntityName() {
        return "Category";
    }
}
