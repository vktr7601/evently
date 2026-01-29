package com.company.ticket_service.payment;

import com.company.ticket_service.account.Account;
import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends BaseEntity {
    @OneToMany
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "external_payment_id")
    private String externalPaymentId;

}
