package com.evently.events.category.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryRequest implements Serializable {
    @NotBlank
    @Size(min = 1, max = 100)
    @JsonProperty("name")
    private String name;
}