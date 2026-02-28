package com.evently.payment.payments.paymentTransactions.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "payment_transactions")
public class PaymentTransactions extends BaseEntity {
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "userId")
    private long userId;
    @Column(name = "transactionId")
    private String transactionId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "receiptUrl")
    private String receiptUrl;
    @Column(name = "providerName")
    private String providerName;
    @Column(name = "paymentTransactionStatus")
    @Enumerated(EnumType.STRING)
    private PaymentTransactionStatus paymentTransactionStatus;
}