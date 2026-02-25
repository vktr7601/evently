package events.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TicketsCreated implements Serializable {
    @JsonProperty("eventLocationIds")
    private List<Long> eventLocationIds;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("categoryIds")
    private List<Long> categoryIds;

    @Override
    public String toString() {
        return "TicketsCreated{" +
                "eventLocationIds=" + eventLocationIds +
                '}';
    }
}