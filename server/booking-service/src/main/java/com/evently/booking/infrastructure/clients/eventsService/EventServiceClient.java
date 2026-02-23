package com.evently.booking.infrastructure.clients.eventsService;

import com.evently.booking.infrastructure.clients.eventsService.data.EventsLocationsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "event-service", url = "http://localhost:8082")
public interface EventServiceClient {

    @PostMapping("/internal/event-locations/check-state/{id}")
    ResponseEntity<Boolean> checkEventLocationsStateById(@PathVariable Long id);
//
    @PostMapping("/internal/event-locations/byIds")
    List<EventsLocationsDto> getLocations(@RequestBody List<Long> ids);
}