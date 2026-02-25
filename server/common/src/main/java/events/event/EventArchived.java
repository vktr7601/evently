package events.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * Domain event published when an event has reached its natural conclusion,
 * triggered when the current time surpasses the event's scheduled end time.
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventArchived implements Serializable {
    @JsonProperty("eventLocationsId")
    private List<Long> eventLocationId;
}