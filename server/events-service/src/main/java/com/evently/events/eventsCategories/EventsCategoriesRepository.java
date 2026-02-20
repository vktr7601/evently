package com.evently.events.eventsCategories;

import com.evently.events.category.entities.CategoryDto;
import com.evently.events.eventsCategories.entities.EventCategoriesDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface EventsCategoriesRepository extends BaseRepository<EventsCategories> {
    @Query(value = """
        SELECT new com.evently.events.eventsCategories.entities.EventCategoriesDto(ec.event.id, c)
        FROM EventsCategories ec
        JOIN ec.category c
        WHERE c.name= :categoryName
        """)
    List<EventCategoriesDto> findAllEventsByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = """
              SELECT new com.evently.events.category.entities.CategoryDto( ec.id, ec.name)
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

    @Modifying
    @Query(value = """
        DELETE FROM EventsCategories ec
        WHERE ec.event.id = :eventId
        """)
    void deleteEventsCategoriesByEventId(@Param("eventId") long eventId);
}