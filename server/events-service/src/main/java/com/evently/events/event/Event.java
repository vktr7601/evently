package com.evently.events.event;

import com.evently.events.eventsLocations.EventsLocations;
import com.evently.events.artists.Artist;
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
    public List<EventsLocations> eventsVenues;
    @Column(name = "name", unique = true, nullable = false, length = 256)
    private String name;
    @Column(name = "description", nullable = false, length = 1024)
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    public Artist artist;
}