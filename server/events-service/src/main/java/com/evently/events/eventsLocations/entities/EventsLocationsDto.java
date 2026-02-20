package com.evently.events.eventsLocations.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor
public class EventsLocationsDto implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("eventId")
    private long eventId;
    @JsonProperty("locationId")
    private long locationId;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("locationName")
    private String locationName;
    @JsonProperty("eventStartTime")
    private LocalDateTime eventStartTime;
    @JsonProperty("eventsLocationsStatus")
    private EventsLocationsStatus eventsLocationsStatus;
    @JsonProperty("pricePerTicket")
    private BigDecimal pricePerTicket;
    @JsonProperty("ticketsCount")
    private long ticketsCount;

//    public EventsLocationsDto(long eventLocationId, long eventId,
//                              long locationId, String eventName,
//                              String locationName,
//                              LocalDateTime eventStartTime,
//                              BigDecimal pricePerTicket,
//                              EventsLocationsStatus eventsLocationsStatus,
//                              long ticketsCount) {
//        setId(eventLocationId);
//        setEventId(eventId);
//        setLocationId(locationId);
//        setEventName(eventName);
//        setLocationName(locationName);
//        setEventStartTime(eventStartTime);
//        setPricePerTicket(pricePerTicket);
//        setEventsLocationsStatus(eventsLocationsStatus);
//        setTicketsCount(ticketsCount);
//    }
}