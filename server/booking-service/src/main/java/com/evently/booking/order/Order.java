package com.evently.booking.order;

import com.evently.booking.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import utils.BaseEntity;
import utils.NumberGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "total_price")
    private BigDecimal totalPrice = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();
    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;
    @Column(name = "number")
    private Long number;
    @Column(name = "active")
    private boolean active;
    @Column(name = "audit")
    @JdbcTypeCode(SqlTypes.JSON)
    private String audit;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "refund_id")
    private String refundId;
    @Column(name = "refund_time")
    private LocalDateTime refundTime;

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setOrder(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        expirationTime = LocalDateTime.now().plusMinutes(10);
        number = NumberGenerator.generateUniqueNumber();
    }
}