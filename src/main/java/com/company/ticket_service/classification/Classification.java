package com.company.ticket_service.classification;

import com.company.ticket_service.accountPreferences.AccountPreferences;
import com.company.ticket_service.core.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "classifications",
        indexes = {@Index(name = "idx_category_name", columnList = "name")},
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Classification extends BaseEntity implements Serializable {
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @OneToMany(mappedBy = "classification")
    private List<AccountPreferences> accountPreferences = new ArrayList<>();

}
