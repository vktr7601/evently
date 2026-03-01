package com.evently.payment.payments.provider.model;

import java.time.Instant;

public class RefundResponse {
    private boolean success;
    private String refundId;
    private String receiptUrl;
    private String message;
    private Instant timestamp;
}