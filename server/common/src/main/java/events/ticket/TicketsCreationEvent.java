package events.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/*
 * Model representing the data required to create tickets when a newly
 * created event is added.
 * It contains the event location identifier, number of tickets, event
 * date/time, and the price per ticket.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TicketsCreationEvent implements Serializable {
    @JsonProperty("eventsLocationsId")
    private long eventLocationId;
    @JsonProperty("ticketsCount")
    private long ticketsCount;
    @JsonProperty("eventStartTime")
    private LocalDateTime eventStartTime;
    @JsonProperty("pricePerTicket")
    private BigDecimal pricePerTicket;
}