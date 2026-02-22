package com.evently.users.user.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "First name is required")
    @JsonProperty("firstName")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @JsonProperty("lastName")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @JsonProperty("email")
    private String email;
    @NotBlank(message = "Password is required")
    @JsonProperty("password")
    private String password;
    @NotBlank(message = "Please confirm your password")
    @JsonProperty("confirmPassword")
    private String confirmPassword;
    @JsonProperty("age")
    private int age;
    @JsonProperty("eventsCategories")
    private List<Long> categories;
    @JsonProperty("locations")
    private List<Long> locations;
    @JsonProperty("isSubscribedToNewsletter")
    private boolean isSubscribedToNewsletter;
}