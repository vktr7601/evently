package com.company.ticket_service.ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import com.company.ticket_service.event.Event;
import com.company.ticket_service.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketService {

  private final TicketsRepository ticketsRepository;

  @Transactional
  public void createTickets(Event event, User user, int ticketsCount) {
    List<Ticket> tickets =
        IntStream.range(0, ticketsCount)
            .mapToObj(
                x -> {
                  Ticket ticket = new Ticket();
                  ticket.setEvent(event);
                  ticket.setPrice(event.getPrice());
                  ticket.setOwner(user);
                  ticket.setBookDate(LocalDateTime.now());
                  return ticket;
                })
            .toList();

    ticketsRepository.saveAll(tickets);
  }
}