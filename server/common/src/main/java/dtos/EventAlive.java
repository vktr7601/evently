package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventAlive implements Serializable {
    @JsonProperty("event_id")
    long eventId;
    @JsonProperty("categories")
    List<Long> categories;
    @JsonProperty("performer_id")
    long performer;
    @JsonProperty("event_name")
    String eventName;
}