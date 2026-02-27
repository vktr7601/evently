package com.evently.events.config.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileSizeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxFileSize {
    String message() default "File size exceeds the allowed limit";

    long maxSizeInMB() default 2;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}