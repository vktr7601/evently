package com.evently.events.event;


import dtos.CreateTicketsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "booking-service")
public interface TicketClient {
    @PostMapping("/create-tickets")
    boolean createTickets(@RequestBody List<CreateTicketsDto> data);
}
