package com.evently.users.config.seed.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSeed implements Serializable {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("age")
    private int age;
    @JsonProperty("email")
    private String email;
    @JsonProperty("userRole")
    private String userRole;
    @JsonProperty("shouldReceiveNotification")
    private boolean shouldReceiveNotification;
    @JsonProperty("password")
    private String password;
}