package com.evently.payment.payments.gateway;

import java.math.BigDecimal;

public interface PaymentGateway {
    PaymentGatewayResponse charge(BigDecimal amount, String cardNumber, String cardExpiry, String cardCvv);
}