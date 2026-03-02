package events.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import events.BaseKafkaEvent;
import events.ticket.TicketsCreationEvent;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class EventUpdated extends BaseKafkaEvent implements Serializable {
    @JsonProperty("eventId")
    private long eventId;

    @JsonProperty("newTicketsToCreate")
    private List<TicketsCreationEvent> newTicketsToCreate;
}