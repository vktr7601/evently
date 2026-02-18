package com.evently.booking.ticket;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = """
        SELECT CASE WHEN COUNT(t) >= :ticketsCount THEN true ELSE false END
        FROM Ticket t
        WHERE t.eventLocationsId = :eventLocationId
        AND t.status = 'AVAILABLE'
        """)
    boolean hasAvailableSeats(
        @Param("eventLocationId") long eventLocationId,
        @Param("ticketsCount") long ticketsCount);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM Ticket t
        WHERE t.eventLocationsId = :eventLocationId
          AND t.dateTime = :startTime
        """)
    boolean isPersisted(@Param("eventLocationId") long eventLocationId, @Param("startTime") LocalDateTime startTime);

    @Query(value = """
        SELECT *
        FROM tickets t
        WHERE t.events_locations_id = :eventLocationId
          AND t.date = :startTime
          AND t.status = 'AVAILABLE'
        LIMIT :ticketCount
        """, nativeQuery = true)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = {@QueryHint(name = "javax.persistence.query.timeout", value = "5000")})
    List<Ticket> findAvailableTicketsForEvent(@Param("eventLocationId") long eventId, @Param("startTime") LocalDateTime startTime, @Param("ticketCount") int count);

    @Query(value = """
        SELECT t
        FROM Ticket t
        WHERE t.eventLocationsId = :eventLocationId
          AND t.dateTime = :startTime
          AND t.status = 'AVAILABLE'
        """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = {@QueryHint(name = "javax.persistence.query.timeout", value = "5000")})
    List<Ticket> findAvailableTicketsForEvent(@Param("eventLocationId") long eventId, @Param("startTime") LocalDateTime startTime, Pageable pageable);

    @Query(value = """
        SELECT t
        FROM Ticket t
        WHERE t.order.id = :orderId
        """)
    List<Ticket> findAllByOrderId(@Param("orderId") long orderId);

    List<Ticket> findAllByUserId(long userId);

    @Query(value = """
        SELECT t
        FROM Ticket t
        WHERE t.userId = :userId
          AND t.id = :ticketId
        """)
    Ticket findByUserIdAndTicketId(@Param("userId") long userId, @Param("ticketId") long ticketId);
}