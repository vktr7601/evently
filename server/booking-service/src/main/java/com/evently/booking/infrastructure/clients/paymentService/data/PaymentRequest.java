package com.evently.booking.infrastructure.clients.paymentService.data;

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
    @JsonProperty("stripePaymentMethodId")
    private String stripePaymentMethodId;

}