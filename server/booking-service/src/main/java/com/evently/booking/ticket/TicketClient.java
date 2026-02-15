package com.evently.booking.ticket;

import dtos.TicketAllocation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(name = "booking-service")
public interface TicketClient {
    @PostMapping("/create-tickets")
    boolean createTickets(@RequestBody List<TicketAllocation> data);
}