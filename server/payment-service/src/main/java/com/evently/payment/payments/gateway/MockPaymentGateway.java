package com.evently.payment.payments.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Component
public class MockPaymentGateway implements PaymentGateway {

    private static final Logger log = LoggerFactory.getLogger(MockPaymentGateway.class);

    @Override
    public PaymentGatewayResponse charge(BigDecimal amount, String cardNumber, String cardExpiry, String cardCvv) {
            log.info("Mock gateway processing payment: amount={}, card=****{}", amount, maskCard(cardNumber));
        String cardRegex = "^\\d{16}$";

        if (cardNumber == null || !cardNumber.matches(cardRegex)) {
            return new PaymentGatewayResponse(null, false, "Invalid card format: must be 13-19 digits");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new PaymentGatewayResponse(null, false, "Invalid amount");
        }

        if (isCardExpired(cardExpiry)) {
            return new PaymentGatewayResponse(null, false, "Card expired");
        }

        if (cardNumber.endsWith("0000")) {
            return new PaymentGatewayResponse(null, false, "Card declined");
        }

        if (cardNumber.endsWith("1111")) {
            return new PaymentGatewayResponse(null, false, "Insufficient funds");
        }

        String transactionId = "txn_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        log.info("Mock gateway payment successful: transactionId={}", transactionId);
        return new PaymentGatewayResponse(transactionId, true, "Payment approved");
    }

    private boolean isCardExpired(String cardExpiry) {
        if (cardExpiry == null || cardExpiry.isBlank()) {
            return true;
        }
        try {
            YearMonth expiry = YearMonth.parse(cardExpiry, DateTimeFormatter.ofPattern("MM/yy"));
            return expiry.isBefore(YearMonth.now());
        } catch (DateTimeParseException e) {
            return true;
        }
    }

    private String maskCard(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return cardNumber.substring(cardNumber.length() - 4);
    }
}