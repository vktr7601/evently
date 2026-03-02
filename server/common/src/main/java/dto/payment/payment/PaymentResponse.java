package dto.payment.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    @JsonProperty("transactionId")
    private long transactionId;
    @JsonProperty("isSuccessful")
    private boolean success;
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
}