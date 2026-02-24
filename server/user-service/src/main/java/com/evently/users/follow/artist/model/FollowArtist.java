package com.evently.users.follow.artist.model;

import com.evently.users.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "follow_artist")
public class FollowArtist extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "location_id")
    private long artistId;
}