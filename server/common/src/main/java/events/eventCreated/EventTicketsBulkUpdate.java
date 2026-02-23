package events.eventCreated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class EventTicketsBulkUpdate implements Serializable {
    @JsonProperty("eventId")
    private long eventId;

    @JsonProperty("updates")
    private List<EventTicketsUpdate> updates = new ArrayList<>();
}