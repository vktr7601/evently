package com.evently.events.venues;

import com.evently.events.eventsLocations.EventsLocations;
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
    @Column(name = "description")
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    private List<EventsLocations> eventsVenues;
}