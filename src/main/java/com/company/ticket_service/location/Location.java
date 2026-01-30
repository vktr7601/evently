package com.company.ticket_service.location;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.eventsLocations.EventsLocations;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location extends BaseEntity {
    @Column(name = "name")
    public String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    private List<EventsLocations> eventsLocations;
}
