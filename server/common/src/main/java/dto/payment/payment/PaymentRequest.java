package dto.payment.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("userId")
    private long userId;
    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("paymentProvider")
    private String paymentProvider;
    @JsonProperty("orderNumber")
    private String orderNumber;
}