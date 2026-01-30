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



  @Override
  default String getEntityName() {
    return "Event";
  }
}