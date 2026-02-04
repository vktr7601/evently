package com.evently.events.venues;

import com.evently.events.eventsVenues.EventsVenues;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "venues")
public class Venue extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venue")
    private List<EventsVenues> eventsVenues;
}