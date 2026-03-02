package events.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import events.BaseKafkaEvent;
import jakarta.persistence.Column;
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
public class OrderPaymentSucceededEvent extends BaseKafkaEvent implements Serializable {
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("receiptUrl")
    private String receiptUrl;
    @JsonProperty("userId")
    private long userId;
}