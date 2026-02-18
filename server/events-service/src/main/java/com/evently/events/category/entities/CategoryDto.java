package com.evently.events.category.entities;

import java.io.Serializable;

public record CategoryDto(String name, long id) implements Serializable {
}