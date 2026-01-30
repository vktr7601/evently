package com.company.ticket_service.eventClassification;

import com.company.ticket_service.classification.Classification;
import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "events_classifications", indexes = {@Index(name = "idx_event_id", columnList = "event_id"), @Index(name = "idx_classification_idd", columnList = "classification_id")})
@Getter
@Setter
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
