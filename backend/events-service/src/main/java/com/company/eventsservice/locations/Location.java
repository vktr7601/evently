package com.company.eventsservice.locations;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import utils.BaseEntity;

@Entity
@Table(name = "locations")
public class Location extends BaseEntity {
    @Column(name = "name")
    private String name;
}
