package com.evently.payment.provider.stripe;

import com.evently.payment.provider.contract.PaymentProvider;
import com.evently.payment.provider.dto.PaymentProviderResult;
import com.evently.payment.provider.dto.PaymentStatus;
import com.evently.payment.provider.stripe.dto.StripeRefund;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.*;
import dto.payment.payment.PaymentRequest;
import dto.payment.refund.RefundRequest;
import dto.payment.refund.RefundResponse;
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

    @Override
    public RefundResponse processRefund(RefundRequest refundRequest) {
        StripeRefund stripeRefundRequest = (StripeRefund) refundRequest;

        try {
            RequestOptions options = RequestOptions.builder()
                    .setApiKey(stripeSecretKey)
                    .setIdempotencyKey("refund-" + ((StripeRefund) refundRequest).getStripeTransactionId())
                    .build();

            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(stripeRefundRequest.getStripeTransactionId())
                    .build();

            Refund stripeRefund = Refund.create(params, options);

            if (!"succeeded".equals(stripeRefund.getStatus())) {
                log.warn("Stripe refund did not succeed. Status: {}, " +
                                "TransactionId: {}",
                        stripeRefund.getStatus(),
                        refundRequest.getTransactionId());

                RefundResponse failedResponse = new RefundResponse();
                failedResponse.setSuccess(false);
//                failedResponse.setMessage("Refund was not successful.
//                Status:" +
//                        " " + stripeRefund.getStatus());
                return failedResponse;
            }

            String chargeId = stripeRefund.getCharge();
            Charge charge = Charge.retrieve(chargeId, options);

            RefundResponse response = new RefundResponse();
            response.setSuccess(true);
            response.setRefundTransactionId(stripeRefund.getId());
            response.setReceiptUrl(charge.getReceiptUrl());
            response.setTimestamp(Instant.now());
            response.setRefundedAmount(refundRequest.getAmount());
            return response;

        } catch (StripeException e) {
            log.error("Stripe refund failed for transactionId={}: {}",
                    refundRequest.getTransactionId(), e.getMessage());

            RefundResponse failedResponse = new RefundResponse();
            failedResponse.setSuccess(false);
            failedResponse.setMessage(e.getMessage());
            failedResponse.setTimestamp(Instant.now());
            return failedResponse;
        }
    }

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
    public PaymentProviderResult processPayment(PaymentRequest paymentRequest) {
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

            PaymentProviderResult paymentResult = new PaymentProviderResult();
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
            var paymentResult = new PaymentProviderResult();
            paymentResult.setStatus(PaymentStatus.FAILED.getValue());
            paymentResult.setMessage(e.getMessage());
            paymentResult.setTimestamp(Instant.now());
            paymentResult.setSuccess(false);
            return paymentResult;
        }
    }

}