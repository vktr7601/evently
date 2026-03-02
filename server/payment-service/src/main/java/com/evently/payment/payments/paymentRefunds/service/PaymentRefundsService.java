package com.evently.payment.payments.paymentRefunds.service;

import com.evently.payment.payments.paymentRefunds.model.PaymentRefund;
import com.evently.payment.payments.paymentRefunds.model.RefundStatus;
import com.evently.payment.payments.paymentRefunds.repository.PaymentRefundRepository;
import com.evently.payment.payments.paymentTransactions.model.PaymentTransaction;
import com.evently.payment.payments.paymentTransactions.service.PaymentTransactionsService;
import com.evently.payment.payments.provider.contracts.PaymentProvider;
import dto.payment.refund.RefundRequest;
import dto.payment.refund.RefundResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class PaymentRefundsService {
    private final PaymentRefundRepository paymentRefundsRepository;
    private final PaymentProvider paymentProvider;
    private final PaymentTransactionsService paymentTransactionsService;

    @Transactional
    public RefundResponse handleRefund(RefundRequest refundRequest) {

        PaymentTransaction originalTransaction = paymentTransactionsService
                .findByTransactionId(refundRequest.getTransactionId());


        PaymentRefund paymentRefund = new PaymentRefund();
        paymentRefund.setTransaction(originalTransaction);
        paymentRefund.setAmount(refundRequest.getAmount());
        paymentRefund.setStatus(RefundStatus.PENDING);
        paymentRefund.setRefundType(refundRequest.getRefundType());
        paymentRefund.setRequestedBy(refundRequest.getRequestedBy());
        paymentRefund.setRequestedAt(Instant.now());

        paymentRefundsRepository.save(paymentRefund);

        RefundResponse result = paymentProvider.processRefund(refundRequest);

        if (result.isSuccess()) {
            paymentRefund.setStatus(RefundStatus.SUCCEEDED);
            paymentRefund.setProviderRefundId(result.getRefundTransactionId());
            paymentRefund.setReceiptUrl(result.getReceiptUrl());
            paymentRefund.setProcessedAt(Instant.now());
        } else {
            paymentRefund.setStatus(RefundStatus.FAILED);
            paymentRefund.setFailureReason(result.getFailureReason());
        }

        paymentRefundsRepository.save(paymentRefund);
        return result;
    }
}