package com.evently.booking.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "events-service-client", url = "http://localhost:8082")
public interface EventsLocationsClient {
    @PostMapping("/internal/event-locations/by-ids")
    Map<Long, EventsLocationsInterface> getLocations(@RequestBody List<Long> ids);
}