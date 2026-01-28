package com.company.ticket_service.event;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.eventCategories.EventsClassifications;
import com.company.ticket_service.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.company.ticket_service.core.NumberGenerator.generateUniqueNumber;

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

  @Column(name = "total_tickets", nullable = false)
  private int totalTickets;

  @Column(name = "booked_tickets")
  private int bookedTickets = 0;

  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "remaining_tickets", nullable = false)
  private int remainingTickets;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private List<EventsClassifications> eventsClassifications;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private List<Ticket> tickets;

  @Override
  public void onCreate() {
    super.onCreate();
    this.remainingTickets = totalTickets;
    this.number = generateUniqueNumber();
  }
}