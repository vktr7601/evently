package events.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventTicketsUpdate implements Serializable {
    @JsonProperty("eventLocationId")
    private long eventLocationId;

    // The actual values
    private LocalDateTime newStartTime;
    private Integer newTicketsCount;
    private BigDecimal newPrice;

    // The "Dirty" Flags
    private boolean isDateUpdated;
    private boolean isTicketCountUpdated;
    private boolean isPriceUpdated;

}