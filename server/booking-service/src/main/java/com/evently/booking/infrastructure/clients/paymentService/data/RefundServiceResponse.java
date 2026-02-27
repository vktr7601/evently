package com.evently.booking.infrastructure.clients.paymentService.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundServiceResponse {
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