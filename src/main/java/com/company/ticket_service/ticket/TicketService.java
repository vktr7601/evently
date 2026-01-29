package com.company.ticket_service.ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import com.company.ticket_service.account.Account;
import com.company.ticket_service.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketService {

    private final TicketsRepository ticketsRepository;

    @Transactional
    public void createTickets(Event event, Account account, int ticketsCount) {
        List<Ticket> tickets =
                IntStream.range(0, ticketsCount)
                        .mapToObj(
                                x -> {
                                    Ticket ticket = new Ticket();
                                    ticket.setEvent(event);
                                    //  ticket.setPrice(event.getPrice());
                                    ticket.setOwner(account);
                                    ticket.setBookDate(LocalDateTime.now());
                                    return ticket;
                                })
                        .toList();

        ticketsRepository.saveAll(tickets);
    }
}