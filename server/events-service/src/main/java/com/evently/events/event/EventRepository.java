package com.evently.events.event;

import com.evently.events.event.entities.EventDetailsDto;
import com.evently.events.event.entities.EventListDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends BaseRepository<Event> {
    boolean existsByName(String name);

//    @Query(value = """
//SELECT new com.evently.events.event.entities.EventDetailsDto(
//    e.name,
//    e.description,
//    e.imageUrl,
//    null,
//    a.name,
//    e.id)
//from Event  as e
//JOIN Artist as  a on Event.artist.id = a.id
//WHERE Event.id = :id
//""")
//    EventDto getEventById(Long id);

    @Query(value = """
            SELECT new com.evently.events.event.entities.EventDetailsDto(
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
    Optional<EventDetailsDto> findEventDtoById(@Param("id") Long id);

    @Query(value = """
            SELECT new com.evently.events.event.entities.EventListDto(
                e.id,
                e.name,
                e.description,
                e.imageUrl,
                a
            )
            FROM Event e
            JOIN Artist a ON e.artist.id = a.id
            ORDER BY e.createdAt DESC
        """)
    List<EventListDto> findAllEventsSortedByDateDesc();

    default String getEntityName() {
        return "Event";
    }
}