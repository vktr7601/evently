package com.evently.users.user;

import com.evently.users.artistFollow.ArtistFollow;
import com.evently.users.categoryFollow.CategoryFollow;
import com.evently.users.locationFollow.LocationFollow;
import com.evently.users.user.entities.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Min(1)
    @Max(120) // 150 is a bit high, but 120 is safer for logic
    @Column(name = "age", nullable = false)
    private int age;

    @NotBlank(message = "Email is required")
    @Email(regexp = ".+@.+\\..+", message = "Please provide a valid email " +
            "address")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank // Password should never be blank
    @Size(min = 60, max = 255) // Optimized for BCrypt/Argon2 hashes
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    public UserRole userRole;

    @OneToMany(mappedBy = "user")
    public List<CategoryFollow> categoryFollowList;

    @OneToMany(mappedBy = "user")
    List<LocationFollow> locationFollows;
    @OneToMany(mappedBy = "user")
    List<ArtistFollow> artistFollows;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                ", userPreferencesList=" + categoryFollowList +
                '}';
    }
}