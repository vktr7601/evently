package com.company.ticket_service.core;

import com.company.ticket_service.core.exceptions.InsufficientTicketsException;
import com.company.ticket_service.event.Event;
import com.company.ticket_service.event.EventRepository;
import com.company.ticket_service.ticket.TicketService;
import com.company.ticket_service.ticket.data.TicketRequest;
import com.company.ticket_service.user.User;
import com.company.ticket_service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketBookingService {
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final TicketService ticketService;

  @Transactional
  public void bookTicket(Long eventId, TicketRequest ticketRequest) {
    Event event = eventRepository.findByIdOrThrow(eventId);

    if (ticketRequest.ticketsCount() <= 0) {
      throw new IllegalArgumentException("Ticket count must be positive");
    }
//
//    if (ticketRequest.ticketsCount() > event.getRemainingTickets()) {
//      throw InsufficientTicketsException.notEnough(
//          ticketRequest.ticketsCount(), event.getRemainingTickets());
//    }
//
//    // Update booked tickets count - CRITICAL: This prevents overbooking
//    // remainingTickets is now calculated as totalTickets - bookedTickets
//    event.setBookedTickets(event.getBookedTickets() + ticketRequest.ticketsCount());

    User user = userRepository.findByIdOrThrow(ticketRequest.user());

    ticketService.createTickets(event, user, ticketRequest.ticketsCount());

    eventRepository.save(event);
  }
}