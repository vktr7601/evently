package com.evently.payment.payments.payment.model;

import com.evently.payment.payments.providers.stripe.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "receiptUrl")
    private String receiptUrl;
}