package com.evently.events.eventsLocations.controller;

import com.evently.events.eventsLocations.service.EventsLocationsService;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.service.data.FetchMode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/byIds")
    public List<EventsLocationsDto> getLocations(@RequestBody List<Long> ids) {
        return eventsLocationsService.findAllByIdsInRange(ids, FetchMode.BASIC);
    }


    @PostMapping("/check-state/{id}")
    public ResponseEntity<Boolean> checkEventLocationsStateById(@PathVariable Long id) {
        var result = eventsLocationsService.checkEventLocationStateById(id);
        return ResponseEntity.ok(result);
    }
}