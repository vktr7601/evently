package com.evently.events.eventsLocations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "booking-service", url = "http://localhost:8081")
public interface BookingServiceClient {

    @GetMapping("/tickets/availability")
    ResponseEntity<?> checkAvailability(@RequestParam("eventLocationId") long eventLocationId, @RequestParam("ticketsCount") int ticketsCount);

}