package com.evently.users.config.seed.models;

import com.evently.users.user.dto.UserPreferences;
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
public class UserPreferencesSeed implements Serializable {
    @JsonProperty("userId")
    private long userId;
    @JsonProperty("follows")
    private FollowSeed followSeed;
}