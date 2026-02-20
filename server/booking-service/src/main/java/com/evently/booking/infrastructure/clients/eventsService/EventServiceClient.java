package com.evently.booking.infrastructure.clients.eventsService;

import com.evently.booking.infrastructure.clients.eventsService.data.EventsLocationsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "event-service", url = "http://localhost:8082")
public interface EventServiceClient {

    @PostMapping("/internal/event-locations/check-state/{id}")
    Boolean checkEventLocationsStateById(@PathVariable Long id);
//
//    @PostMapping("/internal/event-locations/by-ids")
//    Map<Long, EventsLocationsDto> getLocations(@RequestBody List<Long> ids);

    @PostMapping("/internal/event-locations/byIds")
    List<EventsLocationsDto> getLocations(@RequestBody List<Long> ids);
}