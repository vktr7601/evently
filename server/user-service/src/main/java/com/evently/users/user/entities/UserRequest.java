package com.evently.users.user.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("age")
    private int age;
    @JsonProperty("password")
    private String password;
    @JsonProperty("events_preferences")
    private List<Long> preferences;
}