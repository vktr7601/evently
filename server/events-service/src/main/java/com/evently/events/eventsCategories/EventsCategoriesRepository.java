package com.evently.events.eventsCategories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface EventsCategoriesRepository extends BaseRepository<EventsCategories> {
    @Query(value = """
            SELECT c.name
            FROM EventsCategories ec
            JOIN ec.category c
            WHERE ec.event.id = :eventId
            """)
    List<String> findClassificationNamesByEventId(@Param("eventId") Long eventId);

//    List<EventsClassifications> findAllByEventId(Long eventId);
}