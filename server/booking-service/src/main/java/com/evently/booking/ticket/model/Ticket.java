package com.evently.booking.ticket.model;

import com.evently.booking.order.model.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "event_location_id", nullable = false)
    private Long eventLocationsId;
    @Column(name = "number", unique = true, updatable = false, nullable = false)
    private UUID number;
    @Column(name = "event_start_time")
    private LocalDateTime eventStartTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status")
    private TicketStatus status;
    @Column(name = "price",  nullable = false)
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "originalEventStartTime")
    private LocalDateTime originalEventStartTime;
}