package com.evently.events.event.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // TYPE allows putting it on the class itself
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreateEventValidator.class)
public @interface ValidCreateEvent {
    String message() default "Invalid event data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}