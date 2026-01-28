package com.company.ticket_service.classification;

import com.company.ticket_service.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@Table(
        name = "classifications",
        indexes = {@Index(name = "idx_category_name", columnList = "name")})
@AllArgsConstructor
public class Classification extends BaseEntity implements Serializable {
    @Column(name = "name", unique = true, nullable = false, length = 64)
    private String name;
}