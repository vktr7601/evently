package com.evently.payment.payments;

import com.evently.payment.payments.gateway.PaymentGatewayResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentGatewayResponse processPayment(Long userId,
                                                 PaymentRequest paymentRequest) {
        // 1. Prepare Stripe Parameters
        // Note: Stripe expects amounts in CENTS (e.g., $10.00 = 1000)
        long amountInCents =
                paymentRequest.getAmount().multiply(new BigDecimal(100)).longValue();

        try {
            // 2. Create and Confirm the PaymentIntent in one go
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(amountInCents)
                            .setCurrency("usd")
                            .addExpand("latest_charge")
                            .setPaymentMethod(paymentRequest.getStripePaymentMethodId())
                            .putMetadata("user_id", userId.toString()) //
                            // This is safe and visible in Dashboard
                            .putMetadata("order_id",

                                    String.valueOf(paymentRequest.getOrderId()))
                            // Helps track
                            // in Stripe Dashboard
                            .setConfirm(true) // This triggers the actual charge
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                            .setEnabled(true)
                                            .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                                            .build()
                            )
                            .putMetadata("order_id",
                                    String.valueOf(paymentRequest.getOrderId()))
                            .build();

            // Idempotency Key: Prevents double-charging if the Booking
            // Service retries the request
            RequestOptions options = RequestOptions.builder()
                    //  .setIdempotencyKey("payment-order-" + paymentRequest
                    //  .getOrderId())
                    .setApiKey(
                            "sk_test_51T2p6MAsx12C9RhoRw6cxa25nr2h3imTkkWJQ5321TDSQ5gnPPv6XbqdwP8DFPsKMhlywHrK7ln0gV4XeQHQ5KMn002Lcn0HrR")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params, options);

            // 3. Map Stripe Result to your internal Gateway Response
//            PaymentGatewayResponse gatewayResponse = new
//            PaymentGatewayResponse(
//                    "succeeded".equals(intent.getStatus()),
//                    intent.getId(),
//                    intent.getStatus()
//            );

            // 4. Save to your local Payment Service Database (Audit Trail)
            Payment payment = new Payment();
            payment.setUserId(userId);
            payment.setOrderId(paymentRequest.getOrderId());
            payment.setAmount(paymentRequest.getAmount());
            payment.setTransactionId(intent.getId());
//            payment.setStatus(gatewayResponse.isSuccess() ?
//                    PaymentStatus.COMPLETED : PaymentStatus.FAILED);
            Charge charge = intent.getLatestChargeObject();
            String receiptUrl = charge.getReceiptUrl();
            paymentRepository.save(payment);

            return new PaymentGatewayResponse(intent.getId(), true, "success"
                    , receiptUrl);

        } catch (StripeException e) {
            // Handle Stripe specific errors (Declined card, network issues,
            // etc.)
            log.error("Stripe payment failed for Order {}: {}",
                    paymentRequest.getOrderId(), e.getMessage());

            Payment payment = new Payment();
            payment.setUserId(userId);
            payment.setOrderId(paymentRequest.getOrderId());
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);

            return new PaymentGatewayResponse("dasda", false, e.getMessage(),
                    "");
        }
    }

//    public void getPaymentHistory(long userId) {
//        // Implement logic to retrieve payment history for a user
//    }
//
//    public RefundResponse processRefund(RefundRequest refundRequest) {
//        try {
//            // Simulate network latency (0.5 to 1.5 seconds)
//            Thread.sleep(500 + (long) (Math.random() * 1000));
//
//            // Simulate a 95% success rate
//            if (Math.random() > 0.05) {
//                return new RefundResponse(true, "REF-" + UUID.randomUUID(),
//                        "Refund successful");
//            } else {
//                return new RefundResponse(false, null, "Bank rejected the " +
//                        "refund: Insufficient merchant funds");
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            return new RefundResponse(false, null, "Internal system error");
//        }
//    }
}