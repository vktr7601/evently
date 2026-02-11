package com.evently.events.eventsCategories;

import com.evently.events.category.entities.CategoryDto;
import com.evently.events.eventsCategories.entities.EventCategoriesDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface EventsCategoriesRepository extends BaseRepository<EventsCategories> {
//    @Query(value = """
//        SELECT c.name
//        FROM EventsCategories ec
//        JOIN ec.category c
//        WHERE ec.event.id = :eventId
//        """)
//    List<String> findClassificationNamesByEventId(@Param("eventId") Long eventId);


//    @Query("SELECT ec.event FROM EventsCategories ec WHERE ec.category.id = :categoryId")
//    List<Event> findEventsByCategoryId(@Param("categoryId") Long categoryId);

    @Query(value = """
          SELECT new com.evently.events.category.entities.CategoryDto(ec.name, ec.id)
          FROM EventsCategories c
          JOIN Category ec ON c.category.id = ec.id
          WHERE c.event.id = :id
        """)
    List<CategoryDto> findEventCategoriesByEventId(@Param("id") Long id);

    @Query("""
            SELECT new com.evently.events.eventsCategories.entities.EventCategoriesDto(
                c.event.id,
                ec
            )
            FROM EventsCategories c
            JOIN Category ec ON c.category.id = ec.id
            WHERE c.event.id IN :ids
        """)
    List<EventCategoriesDto> findAllCategoriesByEventIds(@Param("ids") List<Long> ids);
}