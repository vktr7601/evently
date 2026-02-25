package events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseKafkaEvent implements Serializable {
    @JsonProperty("messageId")
    private UUID messageId;

    @JsonProperty("occurredAt")
    private Instant occurredAt;
}