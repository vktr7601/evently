package dtos;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TicketAllocation implements Serializable {
    private long eventLocationId;
    private long ticketsCount;
    private LocalDateTime dateTime;
    private BigDecimal ticketPrice;
}