package com.company.ticket_service.eventsLocations;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.core.NumberGenerator;
import com.company.ticket_service.event.Event;
import com.company.ticket_service.eventsLocations.entities.EventLocationStatus;
import com.company.ticket_service.location.Location;
import com.company.ticket_service.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "events_locations", indexes = {@Index(name = "idx_event_id", columnList = "event_id"), @Index(name = "idx_classification_idd", columnList = "location_id")})
@Entity
@Getter
@Setter
public class EventsLocations extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "number", unique = true, nullable = false)
    private Long number;

    @Column(name = "total_tickets", nullable = false)
    private int totalTickets;

    @Column(name = "booked_tickets")
    private int bookedTickets = 0;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EventLocationStatus eventLocationStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventsLocations")
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        number = NumberGenerator.generateUniqueNumber();
    }
}
