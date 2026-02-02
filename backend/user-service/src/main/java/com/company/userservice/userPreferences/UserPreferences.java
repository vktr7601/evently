package com.company.userservice.userPreferences;

import com.company.userservice.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "user_preferences")
public class UserPreferences extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long classificationId;
}
