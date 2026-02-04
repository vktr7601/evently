package com.evently.events.eventsCategories;

import com.evently.events.category.Category;
import com.evently.events.event.Event;
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


    @Query("SELECT ec.event FROM EventsCategories ec WHERE ec.category.id = :categoryId")
    List<Event> findEventsByCategoryId(@Param("categoryId") Long categoryId);
    @Query(value = """
            SELECT ec.category FROM EventsCategories ec WHERE ec.event.id = :eventId
            """)
    List<Category> findCategoriesByEventId(@Param("eventId") Long eventId);

//    List<EventsClassifications> findAllByEventId(Long eventId);
}