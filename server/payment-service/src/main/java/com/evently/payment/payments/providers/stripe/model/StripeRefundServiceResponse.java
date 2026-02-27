package com.evently.payment.payments.providers.stripe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StripeRefundServiceResponse {
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("refundId")
    private String refundId;
    @JsonProperty("isSuccessful")
    private boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("receiptUrl")
    private String receiptUrl;
}