package com.evently.payment.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequest implements Serializable {
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("amount")
    private BigDecimal amount;
}