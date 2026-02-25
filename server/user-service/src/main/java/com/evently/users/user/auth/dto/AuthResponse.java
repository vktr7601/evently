package com.evently.users.user.auth.dto;

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
public class AuthResponse implements Serializable {
    @JsonProperty("jwtToken")
    private String jwtToken;
    @JsonProperty("userRole")
    private String userRole;
}