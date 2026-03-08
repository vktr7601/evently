package events.promoCode;

import com.fasterxml.jackson.annotation.JsonProperty;
import events.BaseKafkaEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PromoCodeCreated extends BaseKafkaEvent implements Serializable {
    @JsonProperty("promoCode")
    private String promoCode;
    @JsonProperty("expirationTime")
    private Instant expirationDate;
    @JsonProperty("discountPercentage")
    private BigDecimal discountPercentage;
    @JsonProperty("discountType")
    private String discountType;
    @JsonProperty("userId")
    private Long userId;
}