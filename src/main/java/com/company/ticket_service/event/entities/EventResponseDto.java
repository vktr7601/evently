package com.company.ticket_service.event.entities;

import com.company.ticket_service.event.Event;
import com.company.ticket_service.eventsLocations.entities.EventLocationsDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class EventResponseDto implements Serializable {
    private long id;
    private String name;
    private String description;
    private List<String> classifications;
    private List<EventLocationsDto> eventLocationsDtos;

    public static EventResponseDto of(Event event, List<String> classifications, List<EventLocationsDto> eventLocationsDtos) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setName(event.getName());
        eventResponseDto.setDescription(event.getDescription());
        eventResponseDto.setClassifications(classifications);
        eventResponseDto.setEventLocationsDtos(eventLocationsDtos);
        eventResponseDto.setId(event.getId());

        return eventResponseDto;
    }

}
