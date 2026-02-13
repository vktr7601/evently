package com.evently.events.eventsLocations;

import com.evently.events.event.Event;
import com.evently.events.eventsLocations.entities.EventsLocationsStataus;
import com.evently.events.locations.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "events_locations", indexes = {@Index(name = "idx_event_id", columnList = "event_id"), @Index(name = "idx_classification_idd", columnList = "location_id")})
public class EventsLocations extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "total_tickets", nullable = false)
    private int totalTickets;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EventsLocationsStataus eventsLocationsStataus;
}