package com.evently.payment.payments;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    private long userId;
    private long orderId;
    private PaymentStatus status;
    private String transactionId;

}