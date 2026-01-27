package com.company.ticket_service.ticket;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.event.Event;
import com.company.ticket_service.user.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {
  @EqualsAndHashCode.Include
  @Column(name = "number", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_number_seq")
  @SequenceGenerator(name = "ticket_number_seq", sequenceName = "ticket_number_seq", allocationSize = 1, initialValue = 1000)
  private Long number;

  @ManyToOne
  @JoinColumn(name = "event_id")
  private Event event;

  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @Column(name = "book_date")
  private LocalDateTime bookDate;

  @Override
  public void onCreate() {
    super.onCreate();
  }
}