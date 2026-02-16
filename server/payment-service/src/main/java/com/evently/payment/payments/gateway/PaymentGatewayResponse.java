package com.evently.payment.payments.gateway;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentGatewayResponse {
    private final String transactionId;
    private final boolean success;
    private final String message;
}
