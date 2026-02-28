package com.evently.booking.infrastructure.clients.paymentService.data;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PaymentResponse {
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("isSuccessful")
    private boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("receiptUrl")
    private String receiptUrl;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("timestamp")
    private Instant timestamp;
    @JsonProperty("paymentMethod")
    private String paymentMethod;
    @JsonProperty("status")
    private String status;
}