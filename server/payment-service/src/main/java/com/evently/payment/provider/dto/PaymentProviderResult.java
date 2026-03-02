package com.evently.payment.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProviderResult {
    private boolean success;
    private String transactionId;
    private String receiptUrl;
    private BigDecimal amount;
    private Instant timestamp;
    private String paymentMethod;
    private String status;
    private String message;
}