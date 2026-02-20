package com.evently.booking.order.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinishOrderRequest {
    @JsonProperty("card_number")
    private String cardNumber;
    @JsonProperty("card_expiry")
    private String cardExpiry;
    @JsonProperty("card_cvv")
    private String cardCvv;
    @JsonProperty("promo_code")
    private String promoCode;
}