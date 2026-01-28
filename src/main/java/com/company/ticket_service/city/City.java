package com.company.ticket_service.city;

import com.company.ticket_service.core.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class City extends BaseEntity {
    public String name;

    public String date;
}