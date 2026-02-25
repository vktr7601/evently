package com.evently.events.eventsCategories.model;

import com.evently.events.category.model.Category;
import com.evently.events.event.model.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "events_categories")
public class EventsCategories extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "event_id")
    public Event event;
    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;
    @Column(name = "active")
    public boolean active = true;
}