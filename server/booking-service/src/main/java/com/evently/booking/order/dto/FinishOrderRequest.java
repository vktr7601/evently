package com.evently.booking.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinishOrderRequest {
    @JsonProperty("stripePaymentMethodId")
    private String stripePaymentMethodId;
}