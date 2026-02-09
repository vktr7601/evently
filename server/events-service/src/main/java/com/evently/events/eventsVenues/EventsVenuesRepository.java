package com.evently.events.eventsVenues;

import com.evently.events.event.entities.EventDto;
import com.evently.events.eventsVenues.entities.EventOccurrenceDTO;
import com.evently.events.eventsVenues.entities.EventsVenuesDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface EventsVenuesRepository extends BaseRepository<EventsVenues> {
    //    @Query(value = """
//            SELECT new com.evently.events.eventsVenues.entities.EventOccurrenceDTO(
//                loc.name,
//                e.name,
//                el.date,
//                e.description,
//                el.price
//            )
//            FROM EventsVenues  el
//            JOIN el.event e
//            JOIN el.venue loc
//            JOIN e.eventsLocations ec
//            JOIN ec c
//            WHERE c.name = :categoryName
//            ORDER BY el.date
//            """)
    //  List<EventOccurrenceDTO> findAllByCategoryName(@Param("categoryName") String categoryName);
//    List<EventOccurrenceDTO> findallBy(String categoryName);

    @Query(value = """
            SELECT new com.evently.events.eventsVenues.entities.EventOccurrenceDTO(
            loc.name,
            e.name,
            el.date,
            e.description,
            el.price)
            FROM EventsVenues  el
                      JOIN el.event e
                      JOIN el.venue loc
                      WHERE loc.name = :locationName
            """)
    List<EventOccurrenceDTO> finaAllByLocationName(@Param("locationName") String locationName);

    @Query(value = """
            SELECT new com.evently.events.event.entities.EventDto(
            e.name,
                        null ,
            p.name,
            e.id)
            FROM EventsVenues  el
                      JOIN el.event e
                    JOIN Performer as p on e.performer.id =p.id
                      WHERE el.venue.id = :id
            """)
    List<EventDto> findAllByVenueId(@Param("id") long venueId);

    @Query(value = """
        SELECT new  com.evently.events.eventsVenues.entities.EventsVenuesDto(
        e.name,
        loc.name,
        el.date,
        el.price,
        el.totalTickets,
                    loc.id)
        FROM EventsVenues  el
                  JOIN el.event e
                  JOIN el.venue loc
                  WHERE e.id = :id
        """)
    List<EventsVenuesDto> findAllByEventId(@Param("id") long id);

    @Override
    default String getEntityName() {
        return "EventsLocations";
    }
}
