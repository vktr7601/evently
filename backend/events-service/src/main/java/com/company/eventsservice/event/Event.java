package com.company.eventsservice.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends BaseEntity {
}
