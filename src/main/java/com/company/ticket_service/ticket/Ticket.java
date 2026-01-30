package com.company.ticket_service.ticket;

import com.company.ticket_service.account.Account;
import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.event.Event;
import com.company.ticket_service.eventsLocations.EventsLocations;
import com.company.ticket_service.payment.Payment;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static com.company.ticket_service.core.NumberGenerator.generateUniqueNumber;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @Column(name = "number")
    private Long number;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "event_location_id")
    private EventsLocations eventsLocations;

    @Column(name = "book_date")
    private LocalDateTime bookDate;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Override
    public void onCreate() {
        super.onCreate();
        number = generateUniqueNumber();
    }
}
