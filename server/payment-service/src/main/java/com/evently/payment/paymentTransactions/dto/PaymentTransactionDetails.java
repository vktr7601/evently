package com.evently.payment.paymentTransactions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionDetails implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("receiptUrl")
    private String receiptUrl;
    @JsonProperty("providerName")
    private String providerName;
    @JsonProperty("paymentTransactionStatus")
    private String paymentTransactionStatus;
    @JsonProperty("paymentTransactionDateTime")
    private LocalDateTime paymentTransactionDateTime;
}