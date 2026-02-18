package com.evently.events.event.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventLocationLogicValidator implements ConstraintValidator<ValidLocationData, EventsLocationsData> {
    @Override
    public boolean isValid(EventsLocationsData eventsLocationsData, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = true;
        if (eventsLocationsData.getEventDate() == null) {
            addViolation(constraintValidatorContext, "eventDate", "Event date must be provided.");
            isValid = false;
        }

        if (eventsLocationsData.getEventDate().isBefore(java.time.LocalDateTime.now().plusDays(2))) {
            addViolation(constraintValidatorContext, "eventDate", "Event date must be at least 48 hours from now.");
            isValid = false;
        }

        if (eventsLocationsData.getPrice() == null || eventsLocationsData.getPrice().doubleValue() < 0) {
            addViolation(constraintValidatorContext, "price", "Price must be a non-negative value.");
            isValid = false;
        }

        if (eventsLocationsData.getLocationId() == 0) {
            addViolation(constraintValidatorContext, "tickets", "Tickets must be a non-negative value.");
            isValid = false;
        }

        if (eventsLocationsData.getTickets() <= 0) {
            addViolation(constraintValidatorContext, "location", "Location must be provided.");
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