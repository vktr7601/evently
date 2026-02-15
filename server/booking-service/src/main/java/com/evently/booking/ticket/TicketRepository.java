package com.evently.booking.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = """
        SELECT CASE WHEN COUNT(t) >= :ticketsCount THEN true ELSE false END
        FROM Ticket t
        WHERE t.eventLocationsId = :eventLocationId
        AND t.status = 'AVAILABLE'
        """)
    boolean hasAvailableSeats(@Param("eventLocationId") long eventLocationId, @Param("ticketsCount") long ticketsCount);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM Ticket t
        WHERE t.eventLocationsId = :eventLocationId
          AND t.dateTime = :startTime
        """)
    boolean isPersisted(@Param("eventLocationId") long eventLocationId, @Param("startTime") LocalDateTime startTime
    );
}