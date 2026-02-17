package com.evently.payment.payments;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "order_id")
    private long orderId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Column(name = "transaction_id")
    private String transactionId;
}