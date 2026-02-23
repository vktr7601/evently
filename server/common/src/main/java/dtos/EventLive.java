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
@AllArgsConstructor
@NoArgsConstructor
public class EventLive implements Serializable {
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("categoryIds")
    private List<Long> categoryIds;
}