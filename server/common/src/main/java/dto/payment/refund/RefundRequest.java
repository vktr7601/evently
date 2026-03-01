package dto.payment.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequest implements Serializable {
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("refundType")
    private RefundType refundType;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("requestedBy")
    private long requestedBy;
}