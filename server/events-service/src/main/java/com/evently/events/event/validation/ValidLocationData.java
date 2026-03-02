package com.evently.events.event.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventLocationLogicValidator.class)
public @interface ValidLocationData {
    String message() default "Invalid location configuration";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}