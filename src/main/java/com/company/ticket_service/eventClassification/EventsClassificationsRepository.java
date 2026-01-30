package com.company.ticket_service.eventClassification;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsClassificationsRepository extends BaseRepository<EventsClassifications> {

    @Query("""
            SELECT c.name
            FROM EventsClassifications ec
            JOIN ec.classification c
            WHERE ec.event.id = :eventId
            """)
    List<String> findClassificationNamesByEventId(@Param("eventId") Long eventId);

    List<EventsClassifications> findAllByEventId(Long eventId);
}
