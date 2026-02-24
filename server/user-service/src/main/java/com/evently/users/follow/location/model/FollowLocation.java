package com.evently.users.follow.location.model;


import com.evently.users.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "follow_location")
public class FollowLocation extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "location_id")
    private long locationId;
}