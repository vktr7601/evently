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
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 150, message = "Age must be less than 150")
    @Column(name = "age", nullable = false)
    private int age;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
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
        userRole = UserRole.USER;
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