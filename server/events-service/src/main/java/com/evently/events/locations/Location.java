package com.evently.events.locations;

import com.evently.events.eventLocations.EventsLocations;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "locations")
public class Location extends BaseEntity {
    @Column(name = "name")
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    private List<EventsLocations> eventsLocations;
}