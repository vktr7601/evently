package com.company.ticket_service.event;

import com.company.ticket_service.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
public class Event extends BaseEntity {
  @Column(name = "name", unique = true, nullable = false, length = 256)
  private String name;

  @Column(name = "description", nullable = false, length = 1024)
  private String description;

  @Column(name = "number", unique = true, nullable = false)
  private Long number;

  @Column(name = "event_date", nullable = false)
  private LocalDateTime eventDate;

  @Column(name = "total_tickets;", nullable = false)
  private int totalTickets;

  @Column(name = "booked_tickets")
  private int bookedTickets = 0;

  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "remaining_tickets", nullable = false)
  private int remainingTickets;

  public boolean hasAvailableTickets() {
    return remainingTickets > 0;
  }

  public void bookTicket(int tickets) {
    remainingTickets -= tickets;
    bookedTickets += tickets;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    this.remainingTickets = totalTickets;
    this.number = generateUniqueNumber();
  }
}