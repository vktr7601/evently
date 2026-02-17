package com.evently.booking.order.entities;

import com.evently.booking.order.OrderStatus;
import com.evently.booking.ticket.entities.TicketListItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderDetailsDto {
    private long id;
    private long number;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private LocalDateTime expirationTime;
    private Instant createdAt;
    private List<TicketListItem> ticketListItems;
}