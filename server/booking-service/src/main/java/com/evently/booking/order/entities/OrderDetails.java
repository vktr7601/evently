package com.evently.booking.order.entities;

import com.evently.booking.order.data.OrderStatus;
import com.evently.booking.ticket.entities.TicketListItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("number")
    private long number;
    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;
    @JsonProperty("status")
    private OrderStatus status;
    @JsonProperty("expirationTime")
    private LocalDateTime expirationTime;
    @JsonProperty("createdAt")
    private Instant createdAt;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("tickets")
    private List<TicketListItem> ticketListItems;
}