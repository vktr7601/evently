package dtos;

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

    @Override
    public String toString() {
        return "TicketsCreated{" +
                "eventLocationIds=" + eventLocationIds +
                '}';
    }
}