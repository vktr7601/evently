package com.evently.booking.order.entities;

import com.evently.booking.ticket.entities.TicketsDto;

import java.util.List;

public class OrderDto {
    private long id;
    private List<TicketsDto> ticket;
}