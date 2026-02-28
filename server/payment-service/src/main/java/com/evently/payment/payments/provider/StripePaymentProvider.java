package com.evently.payment.payments.provider;

import com.evently.payment.payments.provider.contracts.PaymentProvider;
import com.evently.payment.payments.provider.model.PaymentRequest;
import com.evently.payment.payments.provider.model.PaymentResponse;
import com.evently.payment.payments.provider.model.RefundRequest;
import com.evently.payment.payments.provider.model.RefundResponse;
import com.evently.payment.payments.providers.stripe.model.PaymentStatus;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodAttachParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@Slf4j
public class StripePaymentProvider implements PaymentProvider {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

//    @Override
//    public PaymentProcessingResult process(StripePaymentRequest request) {
//
//    }
//
//    @Override
//    public StripeRefundServiceResponse processRefund(StripePaymentRefund
//    refund) {
//        RefundCreateParams params = RefundCreateParams.builder()
//                .setPaymentIntent(refund.getTransactionId())
//                .build();
//
//        RequestOptions options = RequestOptions.builder()
//                .setApiKey(stripeSecretKey)
//                .setIdempotencyKey("refund-" + refund.getTransactionId())
//                .build();
//
//        try {
//            Refund stripeRefund = Refund.create(params, options);
//            String chargeId = stripeRefund.getChargeObject().getId();
//            Charge charge = Charge.retrieve(chargeId, options);
//            String receiptUrl = charge.getReceiptUrl(); // this is the real
//            if (!"succeeded".equals(stripeRefund.getStatus())) {
//                log.warn("Stripe refund did not succeed. Status: {}, " +
//                                "TransactionId: {}",
//                        stripeRefund.getStatus(), refund.getTransactionId());
//                throw new StripeRefundException(
//                        "Refund was not successful. Status: " +
//                        stripeRefund.getStatus()
//                );
//            }
//
//            StripeRefundServiceResponse response =
//                    new StripeRefundServiceResponse();
//            response.setRefundId(stripeRefund.getId());
//            response.setReceiptUrl(receiptUrl);
//            return response;
//
//        } catch (StripeException e) {
//            log.error("Stripe Refund Failed for transactionId={}: {}",
//                    refund.getTransactionId(), e.getMessage());
//            throw new StripeRefundException("Stripe refund failed: " + e
//            .getMessage(), e);
//        }
//    }

    private String getOrCreateCustomer(long userID, String email,
                                       RequestOptions options) throws StripeException {
        CustomerListParams listParams = CustomerListParams.builder()
                .setEmail(email)
                .setLimit(1L)
                .build();

        CustomerCollection customers = Customer.list(listParams, options);

        if (!customers.getData().isEmpty()) {
            return customers.getData().get(0).getId();
        }

        CustomerCreateParams createParams = CustomerCreateParams.builder()
                .setDescription("Evently User ID: " + userID)
                .setEmail(email)
                .build();

        Customer newCustomer = Customer.create(createParams, options);
        return newCustomer.getId();
    }

    private void handlePaymentMethodAttachment(PaymentMethod pm,
                                               String customerId,
                                               RequestOptions options) throws StripeException {
        if (customerId.equals(pm.getCustomer())) {
            return;
        }

        if (pm.getCustomer() != null) {
            pm.detach(options);
            log.info("Detached PM {} from previous owner", pm.getId());
        }

        pm.attach(PaymentMethodAttachParams.builder().setCustomer(customerId).build(), options);
        log.info("Attached PM {} to Customer {}", pm.getId(), customerId);
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        try {
            RequestOptions options =
                    RequestOptions.builder().setApiKey(stripeSecretKey).build();

            String stripeCustomerId =
                    getOrCreateCustomer(paymentRequest.getUserId()
                            , paymentRequest.getUserEmail(),
                            options);

            PaymentMethod pm =
                    PaymentMethod.retrieve(paymentRequest.getTransactionId()
                            , options);
            handlePaymentMethodAttachment(pm, stripeCustomerId, options);

            long amountInCents =
                    paymentRequest.getAmount().multiply(new BigDecimal(100)).longValue();
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(amountInCents)
                            .setCurrency("usd")
                            .addExpand("latest_charge")
                            .setCustomer(stripeCustomerId)
                            .setPaymentMethod(pm.getId())
                            .setConfirm(true)
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                            .setEnabled(true)
                                            .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                                            .build()
                            )
                            .build();

            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(stripeSecretKey)
                    .setIdempotencyKey("payment-order-" + paymentRequest.getOrderNumber())
                    .build();

            PaymentIntent intent = PaymentIntent.create(params, requestOptions);

            PaymentResponse paymentResult = new PaymentResponse();
            paymentResult.setAmount(paymentRequest.getAmount());

            paymentResult.setPaymentMethod(paymentRequest.getPaymentProvider());
            paymentResult.setTimestamp(Instant.now());
            paymentResult.setSuccess(true);
            paymentResult.setReceiptUrl(intent.getLatestChargeObject().getReceiptUrl());
            paymentResult.setStatus(PaymentStatus.COMPLETED.toString());
            paymentResult.setTransactionId(intent.getId());
            return paymentResult;
        } catch (StripeException e) {
            log.error("Stripe Processing Error for User {}: {}",
                    paymentRequest.getUserEmail(), e.getMessage());
            var paymentResult = new PaymentResponse();
            paymentResult.setStatus(PaymentStatus.FAILED.getValue());
            paymentResult.setMessage(e.getMessage());
            paymentResult.setTimestamp(Instant.now());
            paymentResult.setSuccess(false);
            return paymentResult;
        }
    }

    @Override
    public RefundResponse processRefund(RefundRequest refundRequest) {
        return null;
    }
}