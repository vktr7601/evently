package com.evently.booking.ticket.data;

import com.evently.booking.ticket.Ticket;
import com.evently.booking.ticket.entities.TicketListItem;
import events.eventCreated.TicketsCreationEvent;
import org.springframework.stereotype.Component;

@Component
public class TicketsMapper {
    public Ticket convert(TicketsCreationEvent dto) {
        if (dto == null) {
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setEventLocationsId(dto.getEventLocationId());
        ticket.setPrice(dto.getPricePerTicket());
        ticket.setEventStartTime(dto.getEventStartTime());
        ticket.setOriginalEventStartTime(dto.getEventStartTime());
        ticket.setUserId(null);
        ticket.setStatus(TicketStatus.AVAILABLE);

        return ticket;
    }

    public TicketListItem toListItem(Ticket ticket, String eventName,
                                     String locationName) {
        if (ticket == null) {
            return null;
        }

        return new TicketListItem(
                ticket.getId(),
                ticket.getNumber(),
                eventName,
                locationName,
                ticket.getStatus(),
                ticket.getEventStartTime(),
                ticket.getPrice()
        );
    }
}