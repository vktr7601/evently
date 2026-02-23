package dtos;

public class KafkaTopics {
    private KafkaTopics() {
    }

    public static final String USER_REGISTERED = "user-registered-topic";
    public static final String EVENT_CREATED = "event-created-topic";
    public static final String EVENT_CANCELLED = "event-cancelled-topic";
    public static final String EVENT_FINISHED = "event-finished-topic";
    public static final String TICKETS_CREATED = "tickets-created-topic";
    public static final String NEW_EVENT_LOCATIONS_ADDED =  "new-event-locations-added-topic";
}