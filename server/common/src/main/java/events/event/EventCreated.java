package events.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import events.BaseKafkaEvent;
import events.ticket.TicketsCreationEvent;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Domain event published when a new event has been successfully created
 * and persisted, carrying all data required by downstream services to
 * initialise their own state (e.g. ticket inventory, artist indexing,
 * category mapping).
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class EventCreated extends BaseKafkaEvent implements Serializable {
    @JsonProperty("eventId")
    private long eventId;
    @JsonProperty("categories")
    private List<Long> categories;
    @JsonProperty("artistId")
    private long artistId;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("ticketCreateEvents")
    private List<TicketsCreationEvent> ticketsCreationEvents;
}