package com.evently.events.eventsCategories;

import com.evently.events.category.Category;
import com.evently.events.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

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
    public boolean active;

    @Override
    public void onCreate() {
        super.onCreate();
        active = true;
    }
}