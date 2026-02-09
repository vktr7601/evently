package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class EventCreated implements Serializable {
    @JsonProperty("event_id")
    long eventId;
    @JsonProperty("categories")
    List<Long> categories;
    @JsonProperty("performer_id")
    long performer;
    @JsonProperty("event_name")
    String eventName;


    public static EventCreated of(long eventId, List<Long> categories, long performer, String eventName) {
        EventCreated eventCreated = new EventCreated();
        eventCreated.setEventId(eventId);
        eventCreated.setCategories(categories);
        eventCreated.setPerformer(performer);
        eventCreated.setEventName(eventName);
        return eventCreated;
    }
}