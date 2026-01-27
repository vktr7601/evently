package com.company.ticket_service.eventCategories;

import com.company.ticket_service.category.Category;
import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.event.Event;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(
    name = "events_categories",
    uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "category_id"}),
    indexes = {
      @Index(name = "idx_event_id", columnList = "event_id"),
      @Index(name = "idx_category_id", columnList = "category_id")
    })
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
public class EventsCategories extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "event_id", nullable = false)
  @ToString.Exclude
  private Event event;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  @ToString.Exclude
  private Category category;
}