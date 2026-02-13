package com.evently.booking.ticket;

import dtos.CreateTicketsDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public boolean createTickets(List<CreateTicketsDto> data) {
        List<Ticket> list = data.stream().map(ticketMapper::convert).toList();
        log.info("Creating {} tickets", list.size());
        ticketRepository.saveAll(list);
        return true;
    }
}