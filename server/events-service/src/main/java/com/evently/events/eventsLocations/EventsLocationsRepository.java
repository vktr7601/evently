package com.evently.events.eventsLocations;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.entities.EventsLocationsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventsLocationsRepository extends JpaRepository<EventsLocations, Long> {
    @Query(value = """
         SELECT new com.evently.events.eventsLocations.entities.EventsLocationsDto(
                                        el.id,
                                        e.id,
                                        loc.id,
                                        e.name,
                                        loc.name,
                                        el.date,
                                        el.eventsLocationsStatus,
                                        el.price,
                                        el.totalTickets)
            FROM EventsLocations el
            JOIN el.event e ON e.id = el.event.id
            JOIN el.location loc ON loc.id = el.location.id
            WHERE e.id = :eventId AND el.date > CURRENT_DATE
            ORDER BY el.date ASC
        """)
    List<EventsLocationsDto> findUpcomingEventLocationsByEventId(@Param("eventId") long eventId);

    @Query(value = """

            SELECT new com.evently.events.eventsLocations.entities.EventsLocationsDto(
                                             el.id,
                                             e.id,
                                             loc.id,
                                             e.name,
                                             loc.name,
                                             el.date,
                                             el.eventsLocationsStatus,
                                             el.price,
                                             el.totalTickets)
                 FROM EventsLocations el
                 JOIN el.event e ON e.id = el.event.id
                 JOIN el.event.artist a ON a.id = e.artist.id
                 JOIN el.location loc ON loc.id = el.location.id
                 WHERE  el.event.artist.id = :artistId AND el.date > CURRENT_DATE
                 ORDER BY el.date ASC
        """)
    List<EventsLocationsDto> findAllUpcomingEventsByArtistId(@Param("artistId") long artistId);

    @Query(value = """
           SELECT new com.evently.events.eventsLocations.entities.EventsLocationsDto(
                   el.id,
                   e.id,
                   loc.id,
                   e.name,
                   loc.name,
                   el.date,
                   el.eventsLocationsStatus,
                   el.price,
                   el.totalTickets)
            FROM EventsLocations el
            JOIN el.event e ON e.id = el.event.id
            JOIN el.location loc ON loc.id = el.location.id
            WHERE loc.id = :locationId AND el.date > CURRENT_DATE
            ORDER BY el.date ASC
        """)
    List<EventsLocationsDto> findAllUpcomingEventsByLocationId(@Param("locationId") long id);

    @Query(value = """
           SELECT new com.evently.events.eventsLocations.entities.EventsLocationsDto(
                                        el.id,
                                        e.id,
                                        loc.id,
                                        e.name,
                                        loc.name,
                                        el.date,
                                        el.eventsLocationsStatus,
                                        el.price,
                                        el.totalTickets)

            FROM EventsLocations el
            JOIN el.event e ON e.id = el.event.id
            JOIN el.location loc ON loc.id = el.location.id
            WHERE el.id = :id
        """)
    Optional<EventsLocationsDto> findEventLocationById(@Param("id") long id);

    @Query(value = """
          SELECT new com.evently.events.eventsLocations.entities.EventsLocationsDto(
                                        el.id,
                                        e.id,
                                        loc.id,
                                        e.name,
                                        loc.name,
                                        el.date,
                                        el.eventsLocationsStatus,
                                        el.price,
                                        el.totalTickets)
            FROM EventsLocations el
            JOIN el.event e ON e.id = el.event.id
            JOIN el.location loc ON loc.id = el.location.id
            WHERE el.id in :eventLocationIds
        """)
    List<EventsLocationsDto> findAllInList(@Param("eventLocationIds") List<Long> eventLocationIds);

    @Query(value = """
         SELECT CASE COUNT (el) WHEN 0 THEN false ELSE true END
         FROM EventsLocations el
         JOIN el.location loc ON loc.id = el.location.id
        AND el.date >= :startOfDay AND el.date <= :endOfDay
         WHERE loc.id = :locationId
        """)
    boolean hasEventForLocationInSpecificDate(@Param("locationId") long locationId, @Param("startOfDay") LocalDateTime date, @Param("endOfDay") LocalDateTime endDate);
    @Query(value = """
     SELECT el FROM EventsLocations el
     WHERE el.location.id = :locationId
     AND el.date >= :startOfDay
     AND el.date <= :endOfDay
    """)
    Optional<EventsLocations> findEventByLocationAndDate(
            @Param("locationId") long locationId,
            @Param("startOfDay") LocalDateTime start,
            @Param("endOfDay") LocalDateTime end
    );

//    @Query(value = """
//         SELECT el
//         FROM EventsLocations el
//         JOIN el.location loc ON loc.id = el.location.id
//        AND el.date >= :startOfDay AND el.date <= :endOfDay
//         WHERE loc.id = :locationId
//        """)
//    EventsLocations findByEventLocationIdAndEventIdAndStartDate(@Param("locationId") long locationId, @Param("startOfDay") LocalDateTime date, @Param("endOfDay") LocalDateTime endDate);

    @Query("SELECT el.id FROM EventsLocations el WHERE el.date < :now AND el.eventsLocationsStatus = :status")
    List<Long> findIdsByStatusAndDate(
        @Param("now") LocalDateTime now,
        @Param("status") EventsLocationsStatus status
    );

    @Modifying
    @Query("UPDATE EventsLocations el SET el.eventsLocationsStatus = :newStatus WHERE el.id IN :ids")
    void updateStatusByIds(
        @Param("ids") List<Long> ids,
        @Param("newStatus") EventsLocationsStatus newStatus
    );


}