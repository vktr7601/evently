package com.company.ticket_service.ticket;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.event.Event;
import com.company.ticket_service.user.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

import static com.company.ticket_service.core.NumberGenerator.generateUniqueNumber;

@Data
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {
  @Column(name = "number")
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
    number = generateUniqueNumber();
  }
}