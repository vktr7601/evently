package com.evently.events.eventsLocations.model;

import com.evently.events.event.model.Event;
import com.evently.events.locations.model.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "events_locations", indexes = {@Index(name = "idx_event_id",
        columnList = "event_id"), @Index(name = "idx_location_date",
        columnList = "location_id," + " date")}, uniqueConstraints =
        {@UniqueConstraint(name = "uk_event_location_date", columnNames = {
                "event_id", "location_id", "date"})})
public class EventsLocations extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "total_tickets", nullable = false)
    private int totalTickets;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventsLocationsStatus eventsLocationsStatus;
}