package com.evently.events.category.model;

import com.evently.events.eventsCategories.model.EventsCategories;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "categories", indexes = {@Index(name = "idx_category_name",
        columnList = "name")}, uniqueConstraints =
        {@UniqueConstraint(columnNames = "name")})
public class Category extends BaseEntity {
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category")
    public List<EventsCategories> eventsCategories;
}