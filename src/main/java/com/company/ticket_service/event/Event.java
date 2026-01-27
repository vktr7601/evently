package com.company.ticket_service.event;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.eventCategories.EventsCategories;
import com.company.ticket_service.ticket.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "events")
public class Event extends BaseEntity {
  @Version
  @Column(name = "version")
  private Long version;

  @Column(name = "name", unique = true, nullable = false, length = 256)
  private String name;

  @NotBlank(message = "Description cannot be blank")
  @Column(name = "description", nullable = false, length = 1024)
  private String description;

  @EqualsAndHashCode.Include
  @Column(name = "number", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_number_seq")
  @SequenceGenerator(name = "event_number_seq", sequenceName = "event_number_seq", allocationSize = 1, initialValue = 1000)
  private Long number;

  @Future(message = "Event date must be in the future")
  @Column(name = "event_date", nullable = false)
  private LocalDateTime eventDate;

  @Min(value = 1, message = "Total tickets must be at least 1")
  @Column(name = "total_tickets", nullable = false)
  private int totalTickets;

  @Column(name = "booked_tickets")
  private int bookedTickets = 0;

  @DecimalMin(value = "0.01", message = "Price must be greater than zero")
  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private List<EventsCategories> eventsCategories;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private List<Ticket> tickets;

  @Transient
  public int getRemainingTickets() {
    return totalTickets - bookedTickets;
  }

  public boolean hasAvailableTickets() {
    return getRemainingTickets() > 0;
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }
}