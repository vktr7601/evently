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
public class PaymentRequest implements Serializable {
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("stripePaymentMethodId")
    private String stripePaymentMethodId;
    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("userId")
    private long userId;
}