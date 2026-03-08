package com.evently.booking.ticket.data;

import com.evently.booking.ticket.dto.TicketListItem;
import com.evently.booking.ticket.model.Ticket;
import com.evently.booking.ticket.model.TicketStatus;
import events.ticket.TicketsCreationEvent;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public Ticket toEntity(TicketsCreationEvent dto) {
        if (dto == null) {
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setEventLocationsId(dto.getEventLocationId());
        ticket.setPrice(dto.getPricePerTicket());
        ticket.setEventStartTime(dto.getEventStartTime());
        ticket.setOriginalEventStartTime(dto.getEventStartTime());
        ticket.setNumber(TicketNumberGenerator.generateV7());
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
                ticket.getEventLocationsId(),
                eventName,
                locationName,
                ticket.getStatus(),
                ticket.getEventStartTime(),
                ticket.getPrice()
        );
    }
}