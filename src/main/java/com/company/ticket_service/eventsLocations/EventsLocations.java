package com.company.ticket_service.eventsLocations;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.event.Event;
import com.company.ticket_service.location.Location;
import com.company.ticket_service.ticket.Ticket;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table(
        name = "events_locations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "location_id"}),
        indexes = {
                @Index(name = "idx_event_id", columnList = "event_id"),
                @Index(name = "idx_classification_idd", columnList = "location_id")
        })
@Entity
public class EventsLocations extends BaseEntity {
    @Column(name = "event_date", nullable = false)
    private LocalDateTime date;


    @Column(name = "number", unique = true, nullable = false)
    private Long number;

    @Column(name = "total_tickets", nullable = false)
    private int totalTickets;

    @Column(name = "booked_tickets")
    private int bookedTickets = 0;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventsLocations")
    private List<Ticket> tickets;
}