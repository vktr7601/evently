package com.evently.booking.ticket;

import dtos.CreateTicketsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    Ticket convert(CreateTicketsDto dto);
}
