package com.evently.payment.paymentRefunds.dto.mapper;

import com.evently.payment.paymentRefunds.dto.PaymentRefundListItem;
import com.evently.payment.paymentRefunds.model.PaymentRefund;
import org.springframework.stereotype.Component;

@Component
public class PaymentRefundMapper {
    public PaymentRefundListItem toPaymentRefundListItem(PaymentRefund paymentRefund) {
        PaymentRefundListItem paymentRefundListItem =
                new PaymentRefundListItem();
        paymentRefundListItem.setAmount(paymentRefund.getAmount());
        paymentRefundListItem.setReceiptUrl(paymentRefund.getReceiptUrl());
        return paymentRefundListItem;
    }
}