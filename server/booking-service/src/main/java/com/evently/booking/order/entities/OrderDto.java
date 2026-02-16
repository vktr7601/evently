package com.evently.booking.order.entities;

import com.evently.booking.ticket.entities.TicketsDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto implements Serializable {
    private long id;
    private long number;
    private BigDecimal totalPrice;
    private LocalDateTime expirationTime;
    private List<TicketsDto> ticket;
}