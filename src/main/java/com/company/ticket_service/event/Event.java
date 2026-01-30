package com.company.ticket_service.event;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.eventClassification.EventsClassifications;
import com.company.ticket_service.eventsLocations.EventsLocations;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "events")
public class Event extends BaseEntity {
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    public List<EventsLocations> eventsLocations;
    @Column(name = "name", unique = true, nullable = false, length = 256)
    private String name;
    @Column(name = "description", nullable = false, length = 1024)
    private String description;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventsClassifications> eventsClassifications;
}
