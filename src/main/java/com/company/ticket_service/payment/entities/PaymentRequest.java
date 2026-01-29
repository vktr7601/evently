package com.company.ticket_service.payment.entities;

public record PaymentRequest(long accountId, long amount, PaymentProvider paymentProvider) {
}