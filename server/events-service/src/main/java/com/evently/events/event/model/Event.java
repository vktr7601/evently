package com.evently.events.event.model;

import com.evently.events.artists.model.Artist;
import com.evently.events.eventsLocations.model.EventsLocations;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends BaseEntity {
    @OneToMany(mappedBy = "event")
    public List<EventsLocations> eventLocations;
    @Column(name = "name", unique = true, nullable = false, length = 256)
    private String name;
    @Column(name = "description", nullable = false, length = 1024)
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
    @Column(name = "active")
    private boolean isActive = false;
}