package com.evently.booking.ticket.data;

import com.evently.booking.ticket.Ticket;
import com.evently.booking.ticket.entities.TicketListItem;
import events.eventCreated.TicketsCreationEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "eventLocationsId", source = "eventLocationId")
    @Mapping(target = "price", source = "pricePerTicket")
    @Mapping(target = "eventStartTime", source = "eventStartTime")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "number", ignore = true)
    Ticket convert(TicketsCreationEvent dto);

    @Mapping(target = "id", source = "ticket.id")
    @Mapping(target = "number", source = "ticket.number")
    @Mapping(target = "price", source = "ticket.price")
    @Mapping(target = "eventStartTime", source = "ticket.eventStartTime")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventLocationName", source = "locationName")
    TicketListItem toListItem(Ticket ticket, String eventName, String locationName);
}