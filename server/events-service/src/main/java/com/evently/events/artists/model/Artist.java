package com.evently.events.artists.model;

import com.evently.events.event.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "artists")
public class Artist extends BaseEntity {
    @Column(name = "name")
    public String name;
    @Column(name = "bio")
    public String bio;
    @Column(name = "image_url")
    public String imageUrl;
    @OneToMany(mappedBy = "artist")
    public List<Event> events;
}