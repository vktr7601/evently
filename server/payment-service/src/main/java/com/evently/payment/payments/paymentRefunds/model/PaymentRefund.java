package com.evently.payment.payments.paymentRefunds.model;

import com.evently.payment.payments.paymentTransactions.model.PaymentTransaction;
import com.evently.payment.payments.provider.contracts.PaymentProvider;
import dto.payment.refund.RefundType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "payment_refunds")
public class PaymentRefund extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private PaymentTransaction transaction;
    @Column(name = "provider_refund_id", length = 255)
    private String providerRefundId;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private RefundStatus status;
    @Column(name = "refund_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private RefundType refundType;
    @Column(name = "receipt_url")
    private String receiptUrl;
    @Column(name = "requested_by", nullable = false)
    private long requestedBy;
    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;
    @Column(name = "processed_at")
    private Instant processedAt;
    @Column(name = "failure_reason")
    private String failureReason;
}