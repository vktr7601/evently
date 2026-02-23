package events.eventCreated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class EventCreated implements Serializable {
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