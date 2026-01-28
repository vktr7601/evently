package com.company.ticket_service.event;

import com.company.ticket_service.core.BaseRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends BaseRepository<Event> {
  boolean existsByName(String name);

  @Query(
      "SELECT e FROM Event e LEFT JOIN FETCH e.eventsClassifications ec LEFT JOIN FETCH ec.classification WHERE e.id = :id")
  Optional<Event> findByIdWithCategories(@Param("id") Long id);

  @Query(
          "SELECT e FROM Event e LEFT JOIN FETCH e.eventsClassifications ec LEFT JOIN FETCH ec.classification WHERE e.name = :categoryNames")
  List<Event> findAllByCategoryName(@Param("categoryName") String categoryName);

  @Override
  default String getEntityName() {
    return "Event";
  }
}