package com.evently.events.event.repository;

import com.evently.events.event.dto.EventDetailDto;
import com.evently.events.event.dto.EventListItemDto;
import com.evently.events.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByName(String name);

    @Query(value = """
            SELECT new com.evently.events.event.dto.EventDetailDto(
                e.id,
                e.name,
                e.description,
                e.imageUrl,
                a
            )
            FROM Event e
            JOIN Artist a ON e.artist.id = a.id
            WHERE e.id = :id
        """)
    Optional<EventDetailDto> findEventDetailsById(@Param("id") Long id);

    @Query(value = """
            SELECT new com.evently.events.event.dto.EventListItemDto(
                e.id,
                e.name,
                e.description,
                e.imageUrl,
                a
            )
            FROM Event e
            JOIN Artist a ON e.artist.id = a.id
            WHERE e.isActive = true
            ORDER BY e.createdAt DESC
        """)
    List<EventListItemDto> findAllEventsSortedByDateDesc();
    @Query(value = """
            SELECT new com.evently.events.event.dto.EventListItemDto(
                e.id,
                e.name,
                e.description,
                e.imageUrl,
                a
            )
            FROM Event e
            JOIN Artist a ON e.artist.id = a.id
            WHERE e.id IN :ids
            ORDER BY e.createdAt DESC
        """)
    List<EventListItemDto> findAllByEventsIdsIn(List<Long> ids);
}