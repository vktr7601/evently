package com.evently.payment.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    @JsonProperty("order_id")
    private long orderId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("card_number")
    private String cardNumber;
    @JsonProperty("card_expiry")
    private String cardExpiry;
    @JsonProperty("card_cvv")
    private String cardCvv;
}