package com.evently.events.event.entities;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateEventValidator implements ConstraintValidator<ValidCreateEvent, CreateEventRequest> {
    @Override
    public boolean isValid(CreateEventRequest createEventRequest, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = true;

        if (createEventRequest.getDescription().isEmpty() || createEventRequest.getDescription().length() < 10) {
            addViolation(constraintValidatorContext, "description", "Description must be at least 10 characters long.");
            isValid = false;
        }
        if (createEventRequest.getCategories() == null || createEventRequest.getCategories().isEmpty()) {
            addViolation(constraintValidatorContext, "categories", "At least one category must be selected.");
            isValid = false;
        }

        if (createEventRequest.getArtistId() == null) {
            addViolation(constraintValidatorContext, "artistId", "Choose artist for the event.");
            isValid = false;
        }

        if (createEventRequest.getName() == null || createEventRequest.getName().isEmpty() || createEventRequest.getName().length() < 10) {
            addViolation(constraintValidatorContext, "name", "Name must be at least 10 characters long.");
            isValid = false;
        }


        return isValid;
    }


    private void addViolation(ConstraintValidatorContext context, String property, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(property)
            .addConstraintViolation();
    }
}