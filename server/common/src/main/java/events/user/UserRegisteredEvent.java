package events.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import events.BaseKafkaEvent;
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
public class UserRegisteredEvent extends BaseKafkaEvent implements Serializable {
    @JsonProperty("userId")
    private long userId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("followCategories")
    private List<Long> followCategories;
    @JsonProperty("followLocations")
    private List<Long> followLocations;
    @JsonProperty("shouldReceiveNotification")
    private boolean shouldReceiveNotification;
    @JsonProperty("email")
    private String email;
}