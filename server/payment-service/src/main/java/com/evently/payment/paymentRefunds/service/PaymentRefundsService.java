package com.evently.payment.paymentRefunds.service;

import com.evently.payment.paymentRefunds.dto.PaymentRefundListItem;
import com.evently.payment.paymentRefunds.dto.mapper.PaymentRefundMapper;
import com.evently.payment.paymentRefunds.model.PaymentRefund;
import com.evently.payment.paymentRefunds.model.RefundStatus;
import com.evently.payment.paymentRefunds.repository.PaymentRefundRepository;
import com.evently.payment.paymentTransactions.model.PaymentTransaction;
import com.evently.payment.paymentTransactions.model.PaymentTransactionStatus;
import com.evently.payment.paymentTransactions.service.PaymentTransactionsService;
import com.evently.payment.provider.contract.PaymentProvider;
import com.evently.payment.provider.stripe.dto.StripeRefund;
import dto.payment.refund.RefundRequest;
import dto.payment.refund.RefundResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentRefundsService {
    private final PaymentRefundRepository paymentRefundsRepository;
    private final PaymentProvider paymentProvider;
    private final PaymentTransactionsService paymentTransactionsService;
    private final PaymentRefundMapper paymentRefundMapper;

    @Transactional
    public RefundResponse handleRefund(RefundRequest refundRequest) {
        PaymentTransaction originalTransaction = paymentTransactionsService
                .findById(refundRequest.getTransactionId());

        PaymentRefund paymentRefund = new PaymentRefund();
        paymentRefund.setTransaction(originalTransaction);
        paymentRefund.setAmount(refundRequest.getAmount());
        paymentRefund.setStatus(RefundStatus.PENDING);
        paymentRefund.setRefundType(refundRequest.getRefundType());
        paymentRefund.setRequestedBy(refundRequest.getRequestedBy());
        paymentRefund.setRequestedAt(Instant.now());
        paymentRefundsRepository.save(paymentRefund);
        StripeRefund stripeRefund = new StripeRefund();
        stripeRefund.setTransactionId(originalTransaction.getId());
        stripeRefund.setRefundType(refundRequest.getRefundType());
        stripeRefund.setRequestedBy(refundRequest.getRequestedBy());
        stripeRefund.setStripeTransactionId(originalTransaction.getTransactionId());
        stripeRefund.setOrderNumber(originalTransaction.getOrderNumber());
        RefundResponse result = paymentProvider.processRefund(stripeRefund);

        if (result.isSuccess()) {
            paymentRefund.setStatus(RefundStatus.SUCCEEDED);
            originalTransaction.setPaymentTransactionStatus(PaymentTransactionStatus.REFUNDED);
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

//    public List<PaymentRefundListItem> getTransactionRefundList(
//            PaymentTransaction paymentTransaction) {
//        List<PaymentRefundListItem> list =
//                paymentTransaction.getRefunds().stream().map(x -> {
//                    PaymentRefundListItem paymentRefundListItem =
//                            paymentRefundMapper.toPaymentRefundListItem(x);
//                    paymentRefundListItem.setParentTransactionId(paymentTransaction.getId());
//                    return paymentRefundListItem;
//                }).toList();
//
//        return list;
//    }
}