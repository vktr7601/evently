package com.evently.events.eventLocations;

import com.evently.events.eventLocations.entities.EventLocationsDto;
import com.evently.events.eventLocations.entities.EventOccurrenceDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface EventsLocationsRepository extends BaseRepository<EventsLocations> {
    @Query(value = """
            SELECT new com.evently.events.eventLocations.entities.EventOccurrenceDTO(
                loc.name,
                e.name,
                el.date,
                e.description,
                el.price
            )
            FROM EventsLocations  el
            JOIN el.event e
            JOIN el.location loc
            JOIN e.eventsCategories ec
            JOIN ec.category c
            WHERE c.name = :categoryName
            ORDER BY el.date
            """)
    List<EventOccurrenceDTO> findAllByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = """
            SELECT new com.evently.events.eventLocations.entities.EventOccurrenceDTO(
            loc.name,
            e.name,
            el.date,
            e.description,
            el.price)
            FROM EventsLocations  el
                      JOIN el.event e
                      JOIN el.location loc
                      WHERE loc.name = :locationName
            """)
    List<EventOccurrenceDTO> finaAllByLocationName(@Param("locationName") String locationName);

    @Query(value = """
            SELECT new  com.evently.events.eventLocations.entities.EventLocationsDto(
            e.name,
            loc.name,
            el.date,
            el.price,
            el.totalTickets)
            FROM EventsLocations  el
                      JOIN el.event e
                      JOIN el.location loc
                      WHERE e.id = :id
            """)
    List<EventLocationsDto> findAllByEventId(@Param("id") long id);

    @Override
    default String getEntityName() {
        return "EventsLocations";
    }
}