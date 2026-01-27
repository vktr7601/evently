package com.company.ticket_service.ticket;

import com.company.ticket_service.ticket.data.TicketDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
  TicketDto toDto(Ticket ticket);

  Ticket toEntity(TicketDto ticketDto);
}