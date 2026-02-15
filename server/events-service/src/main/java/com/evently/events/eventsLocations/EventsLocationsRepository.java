package com.evently.events.eventsLocations;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                el.price,
                el.eventsLocationsStataus,
                el.totalTickets
            )
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
                el.price,
                el.eventsLocationsStataus,
                el.totalTickets
            )
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
                el.price,
                el.eventsLocationsStataus,
                el.totalTickets
            )
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
                el.price,
                el.eventsLocationsStataus,
                el.totalTickets
            )
            FROM EventsLocations el
            JOIN el.event e ON e.id = el.event.id
            JOIN el.location loc ON loc.id = el.location.id
            WHERE el.id = :eventLocationId
        """)
    EventsLocationsDto findByEventLocationId(long eventLocationId);
}