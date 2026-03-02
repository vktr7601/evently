package com.evently.payment.paymentRefunds.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRefundListItem implements Serializable {
    @JsonProperty("parentTransactionId")
    private long parentTransactionId;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("receiptUrl")
    private String receiptUrl;
    @JsonProperty("amount")
    private BigDecimal amount;
}