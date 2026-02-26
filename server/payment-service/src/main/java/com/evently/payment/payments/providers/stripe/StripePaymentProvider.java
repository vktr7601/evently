package com.evently.payment.payments.providers.stripe;

import com.evently.payment.payments.entities.PaymentProcessingResult;
import com.evently.payment.payments.providers.contracts.PaymentProvider;
import com.evently.payment.payments.providers.stripe.model.StripePaymentRequest;
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

@Service
@Slf4j
public class StripePaymentProvider implements PaymentProvider {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Override
    public PaymentProcessingResult process(StripePaymentRequest request) {
        try {
            RequestOptions options =
                    RequestOptions.builder().setApiKey(stripeSecretKey).build();

            String stripeCustomerId = getOrCreateCustomer(request.getUserId()
                    , request.getUserEmail(),
                    options);

            PaymentMethod pm =
                    PaymentMethod.retrieve(request.getStripePaymentMethodId()
                            , options);
            handlePaymentMethodAttachment(pm, stripeCustomerId, options);

            long amountInCents =
                    request.getAmount().multiply(new BigDecimal(100)).longValue();
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
                    .setIdempotencyKey("payment-order-" + request.getOrderNumber())
                    .build();

            PaymentIntent intent = PaymentIntent.create(params, requestOptions);

            return new PaymentProcessingResult(
                    intent.getId(),
                    "succeeded".equals(intent.getStatus()),
                    intent.getStatus(),
                    intent.getLatestChargeObject() != null ?
                            intent.getLatestChargeObject().getReceiptUrl() : ""
            );

        } catch (StripeException e) {
            log.error("Stripe Processing Error for User {}: {}",
                    request.getUserEmail(), e.getMessage());
            return new PaymentProcessingResult(null, false, e.getMessage(), "");
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
}