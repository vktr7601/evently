package events.eventCreated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class NewLocationsAdded implements Serializable {
    @JsonProperty("eventId")
    private long eventId; // Kept as a parent reference

    @JsonProperty("newTicketsToCreate")
    private List<TicketsCreationEvent> newTicketsToCreate;
}