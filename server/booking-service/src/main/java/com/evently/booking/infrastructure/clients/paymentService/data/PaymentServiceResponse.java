package com.evently.booking.infrastructure.clients.paymentService.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter(PRIVATE)
@NoArgsConstructor
public class PaymentServiceResponse implements Serializable {
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("isSuccessful")
    private boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("receiptUrl")
    private String receiptUrl;
}