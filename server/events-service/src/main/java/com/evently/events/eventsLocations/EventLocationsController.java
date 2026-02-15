package com.evently.events.eventsLocations;

import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/event-locations")
public class EventLocationsController {
    private final EventsLocationsService eventsLocationsService;

    @PostMapping("/by-ids")
    public Map<Long, EventsLocationsDto> getManyByIds(@RequestBody List<Long> ids) {
        return eventsLocationsService.findAllByIdsAsMap(ids);
    }
}