package com.company.ticket_service.eventCategories;

import com.company.ticket_service.classification.Classification;
import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.event.Event;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(
        name = "events_classification_id",
        uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "classification_id"}),
        indexes = {
                @Index(name = "idx_event_id", columnList = "event_id"),
                @Index(name = "idx_classification_idd", columnList = "classification_id")
        })
@Data
public class EventsClassifications extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classification_id", nullable = false)
    @ToString.Exclude
    private Classification classification;
}