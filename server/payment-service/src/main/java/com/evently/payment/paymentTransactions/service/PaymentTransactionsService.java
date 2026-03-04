package com.evently.payment.paymentTransactions.service;

import com.evently.payment.paymentRefunds.dto.PaymentRefundListItem;
import com.evently.payment.paymentRefunds.dto.mapper.PaymentRefundMapper;
import com.evently.payment.paymentTransactions.dto.PaymentTransactionDetails;
import com.evently.payment.paymentTransactions.model.PaymentTransaction;
import com.evently.payment.paymentTransactions.model.PaymentTransactionStatus;
import com.evently.payment.paymentTransactions.repository.PaymentTransactionsRepository;
import com.evently.payment.provider.contract.PaymentProvider;
import com.evently.payment.provider.dto.PaymentProviderResult;
import dto.payment.payment.PaymentRequest;
import dto.payment.payment.PaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PaymentTransactionsService {
    private final PaymentTransactionsRepository paymentTransactionsRepository;
    private final PaymentProvider paymentProvider;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final PaymentRefundMapper paymentRefundMapper;

    @Transactional
    public PaymentResponse charge(PaymentRequest paymentRequest) {
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setAmount(paymentRequest.getAmount());
        transaction.setProviderName(paymentRequest.getPaymentProvider());
        transaction.setUserId(paymentRequest.getUserId());
        transaction.setPaymentTransactionStatus(PaymentTransactionStatus.PENDING);
        transaction.setOrderNumber(paymentRequest.getOrderNumber());
        transaction.setPaymentTransactionDateTime(LocalDateTime.now());
        PaymentProviderResult result =
                paymentProvider.processPayment(paymentRequest);

        if (result.isSuccess()) {
            transaction.setTransactionId(result.getTransactionId());
            transaction.setReceiptUrl(result.getReceiptUrl());
            transaction.setPaymentTransactionStatus(PaymentTransactionStatus.SUCCESS);
        } else {
            transaction.setPaymentTransactionStatus(PaymentTransactionStatus.DECLINED);
            transaction.setFailureReason(result.getMessage());
        }

        paymentTransactionsRepository.save(transaction);

        PaymentResponse response = new PaymentResponse();
        response.setTransactionId(transaction.getId());
        response.setSuccess(result.isSuccess());
        response.setStatus(transaction.getPaymentTransactionStatus().toString());
        response.setMessage(result.getMessage());
        return response;
    }

    public PaymentTransaction findById(Long transactionId) {
        return paymentTransactionsRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("transaction not found"));
    }

    @Transactional
    public PaymentTransactionDetails getPaymentTransactionDetails(long userId,
                                                                  long transactionId) {

        PaymentTransaction paymentTransaction =
                paymentTransactionsRepository.findByIdAndUserId(transactionId
                        , userId).get(0);

        List<PaymentRefundListItem> list =
                paymentTransaction.getRefunds().stream().map(x -> {
                    PaymentRefundListItem paymentRefundListItem =
                            paymentRefundMapper.toPaymentRefundListItem(x);
                    paymentRefundListItem.setParentTransactionId(paymentTransaction.getId());
                    return paymentRefundListItem;
                }).toList();
        PaymentTransactionDetails paymentTransactionDetails =
                paymentTransactionMapper.toPaymentTransactionDetails(paymentTransaction);
        paymentTransactionDetails.setPaymentRefundList(list);

        return paymentTransactionDetails;
    }


    public List<PaymentTransactionDetails> getPaymentTransactionDetails(long userId) {
        List<PaymentTransaction> userTransactions =
                paymentTransactionsRepository.findAllByUserId(userId);

        return userTransactions.stream().map(transaction -> {
            PaymentTransactionDetails details =
                    paymentTransactionMapper.toPaymentTransactionDetails(transaction);

            List<PaymentRefundListItem> refundItems =
                    transaction.getRefunds().stream()
                            .map(refund -> {
                                PaymentRefundListItem item =
                                        paymentRefundMapper.toPaymentRefundListItem(refund);
                                item.setParentTransactionId(details.getId());
                                return item;
                            })
                            .collect(Collectors.toList());

            details.setPaymentRefundList(refundItems);
            return details;
        }).collect(Collectors.toList());
    }
}