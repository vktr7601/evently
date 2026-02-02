package com.company.eventsservice.classification;

import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface ClassificationRepository extends BaseRepository<Classification> {
    boolean existsByName(String name);

    List<Classification> findAllByNameIn(List<String> names);

    default String getEntityName() {
        return "Classification";
    }
}