package com.evently.booking.order.model;

import com.evently.booking.ticket.model.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import persistence.BaseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "userId")
    private long userId;
    @Column(name = "totalPrice")
    private BigDecimal totalPrice = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch =
            FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();
    @Column(name = "expirationTime")
    private Instant expirationTime;
    @Column(name = "number")
    private UUID number;
    @Column(name = "active")
    private boolean active;
    @Column(name = "audit")
    @JdbcTypeCode(SqlTypes.JSON)
    private String audit;
    @Column(name = "transaction_id")
    private long transactionId;

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setOrder(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        expirationTime = Instant.now().plusSeconds(600);
        number = UUID.randomUUID();
    }
}