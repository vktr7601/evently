package com.evently.users.locationFollow;


import com.evently.users.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "location_follow")
public class LocationFollow extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "location_id")
    private long locationId;
}