package com.company.eventsservice.locations;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import utils.BaseEntity;

@Entity
@Table(name = "locations")
public class Location extends BaseEntity {
}
