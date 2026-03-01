package com.evently.booking.infrastructure.clients.paymentService.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponse implements Serializable {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("refundTransactionId")
    private String refundTransactionId;
    @JsonProperty("refundedAt")
    private LocalDateTime refundedAt;
    @JsonProperty("refundedAmount")
    private BigDecimal refundedAmount;
    @JsonProperty("failureReason")
    private String failureReason;
}