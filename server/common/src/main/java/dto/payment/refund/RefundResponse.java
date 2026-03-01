package dto.payment.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundResponse implements Serializable {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("refundId")
    private String refundId;
    @JsonProperty("refundTransactionId")
    private String refundTransactionId;
    @JsonProperty("receiptUrl")
    private String receiptUrl;
    @JsonProperty("refundedAmount")
    private BigDecimal refundedAmount;
    @JsonProperty("timestamp")
    private Instant timestamp;
    @JsonProperty("failureReason")
    private String failureReason;
    @JsonProperty("message")
    private String message;
}