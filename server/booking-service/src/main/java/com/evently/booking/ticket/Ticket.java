package com.evently.booking.ticket;

import com.evently.booking.order.Order;
import com.evently.booking.ticket.data.TicketStatus;
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
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @Column(name = "userId")
    private Long userId;
    @Column(name = "eventLocationId", nullable = false)
    private Long eventLocationsId;
    @Column(name = "number")
    private long number;
    @Column(name = "eventStartTime")
    private LocalDateTime eventStartTime;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public void onCreate() {
        super.onCreate();
        number = NumberGenerator.generateUniqueNumber();
        status = TicketStatus.AVAILABLE;
    }
}