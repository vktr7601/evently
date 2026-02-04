package com.evently.events.eventsVenues;

import com.evently.events.event.Event;
import com.evently.events.venues.Venue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;
import utils.NumberGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "events_venues", indexes = {@Index(name = "idx_event_id", columnList = "event_id"), @Index(name = "idx_classification_idd", columnList = "location_id")})
public class EventsVenues extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

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
    private EventsVenuesStatus eventsVenuesStatus;

    @Override
    public void onCreate() {
        super.onCreate();
        number = NumberGenerator.generateUniqueNumber();
    }
}