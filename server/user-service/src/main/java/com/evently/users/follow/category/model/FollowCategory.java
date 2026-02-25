package com.evently.users.follow.category.model;

import com.evently.users.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "follow_category")
public class FollowCategory extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "category_id")
    private Long categoryId;
}