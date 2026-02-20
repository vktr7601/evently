package com.evently.booking.order.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequest {
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("amount")
    private BigDecimal amount;
}