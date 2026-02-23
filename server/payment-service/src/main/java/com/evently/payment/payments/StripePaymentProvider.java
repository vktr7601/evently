package com.evently.payment.payments;

import com.evently.payment.payments.contracts.PaymentProvider;
import com.evently.payment.payments.entities.PaymentProcessingResult;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class StripePaymentProvider implements PaymentProvider {

  //  @Value("${STRIPE_SECRET_KEY}")
    private String stripeApiKey =
            "sk_test_51T2p6MAsx12C9RhoRw6cxa25nr2h3imTkkWJQ5321TDSQ5gnPPv6XbqdwP8DFPsKMhlywHrK7ln0gV4XeQHQ5KMn002Lcn0HrR";


    @Override
    public PaymentProcessingResult process(Long userId,
                                           PaymentRequest request) {
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
                    RequestOptions.builder().setApiKey(stripeApiKey)
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