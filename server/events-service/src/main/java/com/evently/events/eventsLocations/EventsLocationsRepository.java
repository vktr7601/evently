package com.evently.events.eventsLocations;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface EventsLocationsRepository extends BaseRepository<EventsLocations> {
    //    @Query(value = """
//            SELECT new com.evently.events.eventsLocations.entities.EventOccurrenceDTO(
//                loc.name,
//                e.name,
//                el.date,
//                e.description,
//                el.price
//            )
//            FROM EventsVenues  el
//            JOIN el.event e
//            JOIN el.location loc
//            JOIN e.eventsLocations ec
//            JOIN ec c
//            WHERE c.name = :categoryName
//            ORDER BY el.date
//            """)
    //  List<EventOccurrenceDTO> findAllByCategoryName(@Param("categoryName") String categoryName);
//    List<EventOccurrenceDTO> findallBy(String categoryName);


    //    @Query(value = """
//            SELECT new com.evently.events.event.entities.EventDto(
//            e.name,
//        e.description,
//            e.imageUrl,
//                        null ,
//            p.name,
//            e.id)
//            FROM EventsVenues  el
//                      JOIN el.event e
//                    JOIN Artist as p on e.artist.id =p.id
//                      WHERE el.location.id = :id
//        """)
    //  List<EventDto> findAllByVenueId(@Param("id") long venueId);

    @Query(value = """
            SELECT new com.evently.events.eventsLocations.entities.EventsLocationsDto(
                el.id,
                e.id,
                loc.id,
                e.name,
                loc.name,
                el.date,
                el.price,
                el.eventsLocationsStataus
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
                el.eventsLocationsStataus
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
                el.eventsLocationsStataus
            )
            FROM EventsLocations el
            JOIN el.event e ON e.id = el.event.id
            JOIN el.location loc ON loc.id = el.location.id
            WHERE loc.id = :locationId AND el.date > CURRENT_DATE
            ORDER BY el.date ASC
        """)
    List<EventsLocationsDto> findAllUpcomingEventsByLocationId(@Param("locationId") long id);

    @Override
    default String getEntityName() {
        return "EventsLocations";
    }
}