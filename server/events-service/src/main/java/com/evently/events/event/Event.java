package com.evently.events.event;

import com.evently.events.eventsVenues.EventsVenues;
import com.evently.events.performers.Performer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends BaseEntity {
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    public List<EventsVenues> eventsVenues;
    @Column(name = "name", unique = true, nullable = false, length = 256)
    private String name;
    @Column(name = "description", nullable = false, length = 1024)
    private String description;
    @ManyToOne
    @JoinColumn(name = "performer_id")
    public Performer performer;
}