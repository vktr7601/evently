package com.evently.users.user.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginRequest implements Serializable {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}