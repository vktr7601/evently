package com.evently.payment.payments.providers.stripe;

import com.evently.payment.payments.entities.PaymentProcessingResult;
import com.evently.payment.payments.providers.contracts.PaymentProvider;
import com.evently.payment.payments.providers.stripe.model.StripePaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
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
    public PaymentProcessingResult process(Long userId,
                                           StripePaymentRequest request) {
        try {
            long amountInCents =
                    request.getAmount().multiply(new BigDecimal(100)).longValue();

            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(amountInCents)
                            .setCurrency("usd")
                            .addExpand("latest_charge")
                            .setPaymentMethod(request.getStripePaymentMethodId())
                            .setConfirm(true)
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                            .setEnabled(true)
                                            .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                                            .build()
                            )
                            .build();

            RequestOptions options =
                    RequestOptions.builder().setApiKey(stripeSecretKey)
                            // .setIdempotencyKey("payment-order-" + request
                            // .getOrderId())
                            .build();
            PaymentIntent intent = PaymentIntent.create(params, options);

            return new PaymentProcessingResult(
                    intent.getId(),
                    "succeeded".equals(intent.getStatus()),
                    intent.getStatus(),
                    intent.getLatestChargeObject().getReceiptUrl()
            );
        } catch (StripeException e) {
            return new PaymentProcessingResult(null, false, e.getMessage(), "");
        }
    }
}