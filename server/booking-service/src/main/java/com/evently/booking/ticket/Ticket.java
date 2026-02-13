package com.evently.booking.ticket;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;
import utils.NumberGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "payment_id")
    private Long paymentId;
    @Column(name = "events_locations_id", nullable = false)
    private Long eventLocationsId;
    @Column(name = "number")
    private long number;
    @Column(name = "date")
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Override
    public void onCreate() {
        super.onCreate();
        number = NumberGenerator.generateUniqueNumber();
        status = TicketStatus.AVAILABLE;
    }
}