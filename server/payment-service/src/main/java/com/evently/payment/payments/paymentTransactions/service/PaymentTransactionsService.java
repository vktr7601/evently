package com.evently.payment.payments.paymentTransactions.service;

import com.evently.payment.payments.paymentTransactions.dto.PaymentTransactionDetails;
import com.evently.payment.payments.paymentTransactions.model.PaymentTransaction;
import com.evently.payment.payments.paymentTransactions.model.PaymentTransactionStatus;
import com.evently.payment.payments.paymentTransactions.repository.PaymentTransactionsRepository;
import com.evently.payment.payments.provider.contracts.PaymentProvider;
import com.evently.payment.payments.provider.model.PaymentProviderResult;
import dto.payment.payment.PaymentRequest;
import dto.payment.payment.PaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class PaymentTransactionsService {
    private final PaymentTransactionsRepository paymentTransactionsRepository;
    private final PaymentProvider paymentProvider;
    private final PaymentTransactionMapper paymentTransactionMapper;

    @Transactional
    public PaymentResponse charge(PaymentRequest paymentRequest) {
        PaymentTransaction paymentTransactions = new PaymentTransaction();
        paymentTransactions.setAmount(paymentRequest.getAmount());
        paymentTransactions.setProviderName(paymentRequest.getPaymentProvider());
        paymentTransactions.setTransactionId(paymentRequest.getTransactionId());
        paymentTransactions.setUserId(paymentRequest.getUserId());
        paymentTransactions.setPaymentTransactionStatus(PaymentTransactionStatus.PENDING);
        paymentTransactions.setOrderNumber(paymentRequest.getOrderNumber());
        paymentTransactions.setPaymentTransactionDateTime(LocalDateTime.now());
        paymentTransactionsRepository.save(paymentTransactions);

        PaymentProviderResult paymentProviderResult =
                paymentProvider.processPayment(paymentRequest);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionId(paymentTransactions.getId());
        paymentResponse.setStatus(paymentTransactions.getPaymentTransactionStatus().toString());
        if (paymentProviderResult.isSuccess()) {
            paymentTransactions.setReceiptUrl(paymentProviderResult.getReceiptUrl());
            paymentTransactions.setTransactionId(paymentProviderResult.getTransactionId());
            paymentTransactions.setPaymentTransactionStatus(PaymentTransactionStatus.SUCCESS);
            paymentResponse.setSuccess(true);
        } else {
            paymentTransactions.setTransactionId(paymentProviderResult.getTransactionId());
            paymentTransactions.setReceiptUrl(paymentProviderResult.getReceiptUrl());
            paymentTransactions.setPaymentTransactionStatus(PaymentTransactionStatus.DECLINED);
            paymentResponse.setSuccess(false);
        }
        paymentTransactionsRepository.save(paymentTransactions);

        return paymentResponse;
    }

    public PaymentTransaction findByTransactionId(String transactionId) {
        return paymentTransactionsRepository.findByTransactionId(transactionId).orElseThrow(() -> new RuntimeException("transaction not found"));
    }

    @Transactional
    public PaymentTransactionDetails getPaymentTransactionDetails(long userId,
                                                                  long transactionId) {

        PaymentTransaction paymentTransaction =
                paymentTransactionsRepository.findByIdAndUserId(transactionId
                        , userId).orElseThrow(() -> new RuntimeException(
                        "adasa"));
        PaymentTransactionDetails paymentTransactionDetails =
                paymentTransactionMapper.toPaymentTransactionDetails(paymentTransaction);


        return paymentTransactionDetails;
    }
}