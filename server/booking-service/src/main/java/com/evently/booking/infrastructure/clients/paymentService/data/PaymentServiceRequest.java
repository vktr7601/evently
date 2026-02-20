package com.evently.booking.infrastructure.clients.paymentService.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
public class PaymentServiceRequest implements Serializable {
    @JsonProperty("orderId")
    private long orderId;
    @Column(name = "userId")
    private long userId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("cardNumber")
    private String cardNumber;
    @JsonProperty("cardExpiry")
    private String cardExpiry;
    @JsonProperty("cardCvv")
    private String cardCvv;
}