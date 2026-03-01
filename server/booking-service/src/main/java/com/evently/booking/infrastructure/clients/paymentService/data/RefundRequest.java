package com.evently.booking.infrastructure.clients.paymentService.data;

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
public class RefundRequest implements Serializable {
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("amount")
    private BigDecimal amount;
}