package com.evently.users.user.entities;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // TYPE allows putting it on the class itself
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegisterUserRequestValidator.class)
public @interface ValidRegisterUserRequest {
    String message() default "Invalid event data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}