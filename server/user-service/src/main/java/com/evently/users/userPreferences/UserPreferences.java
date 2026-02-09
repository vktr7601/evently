package com.evently.users.userPreferences;

import com.evently.users.user.User;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "event_category_id")
    private Long eventCategoryId;
}