package com.evently.payment.paymentTransactions.model;

import com.evently.payment.paymentRefunds.model.PaymentRefund;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import persistence.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "payment_transactions")
@DynamicUpdate
public class PaymentTransaction extends BaseEntity {
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "userId")
    private long userId;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "receipt_url", updatable = false)
    private String receiptUrl;
    @Column(name = "provider_name")
    private String providerName;
    @Column(name = "payment_transaction_status")
    @Enumerated(EnumType.STRING)
    private PaymentTransactionStatus paymentTransactionStatus;
    @Column(name = "payment_transaction_date_time")
    private LocalDateTime paymentTransactionDateTime;
    @Column(name = "failure_reason")
    private String failureReason;
    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
    private List<PaymentRefund> refunds = new ArrayList<>();
}