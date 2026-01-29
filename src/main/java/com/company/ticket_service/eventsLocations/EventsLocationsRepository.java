package com.company.ticket_service.eventsLocations;

import com.company.ticket_service.core.BaseRepository;
import com.company.ticket_service.eventsLocations.entities.EventOccurrenceDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsLocationsRepository extends BaseRepository<EventsLocations> {

    /**
     * Find all event occurrences by classification name
     * Using JPQL with constructor expression for type-safe results
     */
    @Query("""
            SELECT new com.company.ticket_service.eventsLocations.EventOccurrenceDTO(
                loc.name,
                e.name,
                el.date,
                e.description,
                el.price
            )
            FROM EventsLocations  el
            JOIN el.event e
            JOIN el.location loc
            JOIN e.eventsClassifications ec
            JOIN ec.classification c
            WHERE c.name = :classificationName
            ORDER BY el.date
            """)
    List<EventOccurrenceDTO> findAllByClassificationName(@Param("classificationName") String classificationName);


    @Query("""
            select  com.company.ticket_service.eventsLocations.EventOccurrenceDTO(
                            loc.name,
                            e.name,
                            el.date,
                            e.description,
                            el.price
                        )
  FROM EventsLocations  el
            JOIN el.event e
            JOIN el.location loc
            JOIN e.eventsClassifications ec
            JOIN ec.classification c
            WHERE loc.name = :locationName
            """)

    List<EventOccurrenceDTO> finaAllByLocationName(@Param("locationName") String locationName);
    @Override
    default String getEntityName() {
        return "EventsLocations";
    }
}
