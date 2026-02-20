package com.evently.users.user.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("age")
    private int age;
    @JsonProperty("password")
    private String password;
    @JsonProperty("confirmPassword")
    private String confirmPassword;
    @JsonProperty("eventsCategories")
    private List<Long> categories;
    @JsonProperty("locations")
    private List<Long> locations;
    @JsonProperty("isSubscribedToNewsletter")
    private boolean isSubscribedToNewsletter;
}