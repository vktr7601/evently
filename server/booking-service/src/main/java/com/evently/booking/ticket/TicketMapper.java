package com.evently.booking.ticket;

import dtos.TicketAllocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "eventLocationsId", source = "eventLocationId")
    @Mapping(target = "price", source = "ticketPrice")
    @Mapping(target = "dateTime", source = "dateTime")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "active", constant = "true")
    Ticket convert(TicketAllocation dto);
}