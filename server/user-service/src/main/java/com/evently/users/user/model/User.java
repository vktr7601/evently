package com.evently.users.user.model;

import com.evently.users.follow.artist.model.FollowArtist;
import com.evently.users.follow.category.model.FollowCategory;
import com.evently.users.follow.location.model.FollowLocation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(
        name = "users",
        indexes = @Index(name = "idx_user_email", columnList = "email")
)
public class User extends BaseEntity {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Min(18)
    @Max(100)
    @Column(name = "age", nullable = false)
    private int age;
    @NotBlank(message = "Email is required")
    @Email(regexp = ".+@.+\\..+", message = "Please provide a valid email " +
            "address")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    @NotBlank
    @Size(min = 60, max = 255)
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<FollowCategory> followCategoryList;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<FollowLocation> followLocationList;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<FollowArtist> followArtistsList;
    @Column(name = "notifications_on")
    private boolean shouldReceiveNotification;
    @Column(name = "last_logged_in")
    private LocalDateTime lastLoggedIn;
}