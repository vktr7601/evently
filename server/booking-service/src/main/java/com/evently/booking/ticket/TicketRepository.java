package com.evently.booking.ticket;

import com.evently.booking.ticket.data.TicketStatus;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
              AND t.eventStartTime = :startTime
            """)
    boolean isPersisted(@Param("eventLocationId") long eventLocationId,
                        @Param("startTime") LocalDateTime startTime);

    @Query(value = """
            SELECT t
            FROM Ticket t
            WHERE t.eventLocationsId = :eventLocationId
              AND t.eventStartTime = :startTime
              AND t.status = 'AVAILABLE'
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = {@QueryHint(name = "javax.persistence.query.timeout",
            value = "5000")})
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
    Ticket findByUserIdAndTicketId(@Param("userId") long userId, @Param(
            "ticketId") long ticketId);

    @Query(value = "SELECT * FROM tickets WHERE event_location_id = " +
            ":eventLocations", nativeQuery = true)
    List<Ticket> findAllByEventLocationsId(@Param("eventLocations") long eventLocations);

//    @Modifying // Required for DML operations
//    @Transactional // Required to allow the update
//    @Query("UPDATE Ticket t SET t.status = :newStatus WHERE t.eventLocation
//    .id IN :ids")
//    int updateTicketStatusByEventLocationIds(
//            @Param("ids") List<Long> ids,
//            @Param("newStatus") TicketStatus newStatus
//    );

    //    @Query("SELECT t FROM Ticket t JOIN FETCH t.order WHERE t
    //    .eventLocationsId.id IN :ids AND t.status != 'REFUNDED'")
//    List<Ticket> findAllTicketsWithOrdersByLocationIds(@Param("ids")
//    List<Long> ids);
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query("UPDATE Ticket t SET t.status = 'DISCARDED' " +
            "WHERE t.eventLocationsId IN :ids AND t.status = 'AVAILABLE'")
    int discardAllUnboughtTickets(@Param("ids") List<Long> ids);


    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.eventLocationsId = :locId " +
            "AND t.status = :status")
    int countByLocationAndStatus(@Param("locId") long locId,
                                 @Param("status") TicketStatus status);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.eventLocationsId = :locId")
    int countByEventLocationId(@Param("locId") long locId);

    @Modifying
    @Query("UPDATE Ticket t SET t.eventStartTime = :startTime WHERE t" +
            ".eventLocationsId = :locationId")
    void updateStartTimeByLocationId(@Param("locationId") Long locationId,
                                     @Param("startTime") LocalDateTime startTime);

    Optional<Ticket> findFirstByEventLocationsId(long locationId);
}