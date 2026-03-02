package com.evently.payment.paymentTransactions.service;

import com.evently.payment.paymentTransactions.dto.PaymentTransactionDetails;
import com.evently.payment.paymentTransactions.model.PaymentTransaction;
import org.springframework.stereotype.Component;

@Component
public class PaymentTransactionMapper {

    public PaymentTransactionDetails toPaymentTransactionDetails(PaymentTransaction paymentTransaction) {
        PaymentTransactionDetails paymentTransactionDetails =
                new PaymentTransactionDetails();
        paymentTransactionDetails.setId(paymentTransaction.getId());
        paymentTransactionDetails.setTransactionId(paymentTransaction.getTransactionId());
        paymentTransactionDetails.setAmount(paymentTransaction.getAmount());
        paymentTransactionDetails.setProviderName(paymentTransaction.getProviderName());
        paymentTransactionDetails.setReceiptUrl(paymentTransaction.getReceiptUrl());
        paymentTransactionDetails.setOrderNumber(paymentTransaction.getOrderNumber());
        paymentTransactionDetails.setPaymentTransactionStatus(paymentTransaction.getPaymentTransactionStatus().toString());
        paymentTransactionDetails.setPaymentTransactionDateTime(paymentTransaction.getPaymentTransactionDateTime());
        return paymentTransactionDetails;
    }
}