package com.company.ticket_service.event;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.eventClassification.EventsClassifications;
import com.company.ticket_service.eventsLocations.EventLocationStatus;
import com.company.ticket_service.eventsLocations.EventsLocations;
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

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventsClassifications> eventsClassifications;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    public List<EventsLocations> eventsLocations;
}