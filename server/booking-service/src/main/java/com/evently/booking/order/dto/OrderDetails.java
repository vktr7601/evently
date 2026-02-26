package com.evently.booking.order.dto;

import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.ticket.dto.TicketListItem;
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
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("number")
    private UUID number;
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
    @JsonProperty("receiptUrl")
    private String receiptUrl;
}