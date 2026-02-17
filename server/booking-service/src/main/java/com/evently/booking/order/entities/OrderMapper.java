package com.evently.booking.order.entities;


import com.evently.booking.order.Order;
import com.evently.booking.ticket.entities.TicketListItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "ticketListItems", source = "listItems")
    @Mapping(target = "status", source = "order.status")
    @Mapping(target = "expirationTime", source = "order.expirationTime")
    @Mapping(target = "id", source = "order.id")
    OrderDetailsDto toDto(Order order, List<TicketListItem> listItems);
}