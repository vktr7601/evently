package com.company.eventsservice.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "classifications",
        indexes = {@Index(name = "idx_category_name", columnList = "name")},
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Category extends BaseEntity {
    @Column(name = "name", nullable = false, length = 64)
    private String name;
}