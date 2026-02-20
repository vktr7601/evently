package com.evently.payment.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    @JsonProperty("orderId")
    private long orderId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("cardNumber")
    private String cardNumber;
    @JsonProperty("cardExpiry")
    private String cardExpiry;
    @JsonProperty("cardCvv")
    private String cardCvv;
}