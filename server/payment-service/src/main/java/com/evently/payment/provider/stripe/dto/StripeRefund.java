package com.evently.payment.provider.stripe.dto;

import dto.payment.refund.RefundRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StripeRefund extends RefundRequest {
    private String stripeTransactionId;
}