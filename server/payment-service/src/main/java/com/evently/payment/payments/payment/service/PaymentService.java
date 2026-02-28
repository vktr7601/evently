//package com.evently.payment.payments.payment.service;
//
//import com.evently.payment.payments.entities.PaymentProcessingResult;
//import com.evently.payment.payments.payment.model.Payment;
//import com.evently.payment.payments.payment.repository.PaymentRepository;
//import com.evently.payment.payments.providers.contracts.PaymentProvider;
//import com.evently.payment.payments.providers.stripe.model.PaymentStatus;
//import com.evently.payment.payments.providers.stripe.model.StripePaymentRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class PaymentService {
//    private final PaymentRepository paymentRepository;
//    private final com.evently.payment.payments.provider.contracts.PaymentProvider paymentProvider;
//
//    @Transactional
//    public PaymentProcessingResult processPayment(
//            StripePaymentRequest paymentRequest) {
//        PaymentProcessingResult paymentProcessingResult =
//                paymentProvider.processPayment(
//                        paymentRequest);
//
//        Payment payment = new Payment();
//        payment.setUserId(paymentRequest.getUserId());
//        payment.setOrderNumber(paymentRequest.getOrderNumber());
//        payment.setAmount(paymentRequest.getAmount());
//        payment.setTransactionId(paymentProcessingResult.getTransactionId());
//        payment.setStatus(paymentProcessingResult.isSuccess() ?
//                PaymentStatus.COMPLETED :
//                PaymentStatus.FAILED);
//        payment.setReceiptUrl(paymentProcessingResult.getReceiptUrl());
//
//        paymentRepository.save(payment);
//
//        return paymentProcessingResult;
//    }
//
////    @Transactional
////    public StripePaymentRefund processRefund(StripePaymentRefund
////    stripePaymentRefund) {
////        PaymentProvider paymentProvider = paymentProviders.get(0);
////
////        StripePaymentRequest stripePaymentRequest =
////                paymentProvider.processRefund(stripePaymentRefund);
////
////        System.out.println();
////        return stripePaymentRefund;
////    }
//}