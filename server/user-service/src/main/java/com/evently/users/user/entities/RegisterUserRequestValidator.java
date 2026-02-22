package com.evently.users.user.entities;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegisterUserRequestValidator  implements ConstraintValidator<ValidRegisterUserRequest,
        UserRequest> {
    @Override
    public boolean isValid(UserRequest userRequest, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}