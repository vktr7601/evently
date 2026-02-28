package com.evently.payment.payments.providers.stripe.model;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("pending"),
    COMPLETED("completed"),
    FAILED("failed");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}