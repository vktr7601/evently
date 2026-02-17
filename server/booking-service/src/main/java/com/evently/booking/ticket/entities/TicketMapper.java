package com.evently.booking.ticket.entities;

import com.evently.booking.ticket.Ticket;
import dtos.TicketAllocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "eventLocationsId", source = "eventLocationId")
    @Mapping(target = "price", source = "ticketPrice")
    @Mapping(target = "dateTime", source = "dateTime")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "number", ignore = true)
    Ticket convert(TicketAllocation dto);


    @Mapping(target = "id", source = "ticket.id")
    @Mapping(target = "number", source = "ticket.number")
    @Mapping(target = "price", source = "ticket.price")
    @Mapping(target = "eventDate", source = "ticket.dateTime")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventLocationName", source = "locationName")
    TicketListItem toListItem(Ticket ticket, String eventName, String locationName);
}