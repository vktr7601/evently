package com.evently.payment.payments.paymentTransactions.service;

import com.evently.payment.payments.paymentTransactions.model.PaymentTransactionStatus;
import com.evently.payment.payments.paymentTransactions.model.PaymentTransaction;
import com.evently.payment.payments.paymentTransactions.repository.PaymentTransactionsRepository;
import com.evently.payment.payments.provider.contracts.PaymentProvider;
import dto.payment.payment.PaymentRequest;
import dto.payment.payment.PaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class PaymentTransactionsService {
    private final PaymentTransactionsRepository paymentTransactionsRepository;
    private final PaymentProvider paymentProvider;

    @Transactional
    public PaymentResponse charge(PaymentRequest paymentRequest) {
        PaymentTransaction paymentTransactions = new PaymentTransaction();
        paymentTransactions.setAmount(paymentRequest.getAmount());
        paymentTransactions.setProviderName(paymentRequest.getPaymentProvider());
        paymentTransactions.setTransactionId(paymentRequest.getTransactionId());
        paymentTransactions.setUserId(paymentRequest.getUserId());
        paymentTransactions.setPaymentTransactionStatus(PaymentTransactionStatus.PENDING);
        paymentTransactions.setOrderNumber(paymentRequest.getOrderNumber());
        paymentTransactionsRepository.save(paymentTransactions);

        var result = paymentProvider.processPayment(paymentRequest);
        if (result.isSuccess()) {
            paymentTransactions.setReceiptUrl(result.getReceiptUrl());
            paymentTransactions.setTransactionId(result.getTransactionId());
            paymentTransactions.setPaymentTransactionStatus(PaymentTransactionStatus.SUCCESS);
            paymentTransactionsRepository.save(paymentTransactions);
        } else {
            paymentTransactions.setTransactionId(result.getTransactionId());
            paymentTransactions.setReceiptUrl(result.getReceiptUrl());
            paymentTransactions.setPaymentTransactionStatus(PaymentTransactionStatus.DECLINED);
        }

        return result;
    }


    public PaymentTransaction findByTransactionId(String transactionId) {
        return paymentTransactionsRepository.findByTransactionId(transactionId).orElseThrow(() -> new RuntimeException("transaction not found"));
    }
}