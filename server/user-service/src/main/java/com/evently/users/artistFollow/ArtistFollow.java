package com.evently.users.artistFollow;

import com.evently.users.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "artist_follow")
public class ArtistFollow extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "location_id")
    private long artistId;
}