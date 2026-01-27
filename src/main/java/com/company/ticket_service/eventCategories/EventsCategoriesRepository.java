package com.company.ticket_service.eventCategories;

import java.util.List;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsCategoriesRepository extends BaseRepository<EventsCategories> {
  List<EventsCategories> findAllByEventId(Long eventId);
}