package com.evently.payment.payments.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessingResult implements Serializable {
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("isSuccessful")
    private boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("receiptUrl")
    private String receiptUrl;
}