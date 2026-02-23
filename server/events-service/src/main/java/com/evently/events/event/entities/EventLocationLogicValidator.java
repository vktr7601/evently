package com.evently.events.event.entities;

import com.evently.events.eventsLocations.entities.EventsLocationsData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventLocationLogicValidator implements ConstraintValidator<ValidLocationData, EventsLocationsData> {
    private static final int MIN_NOTICE_HOURS = 48;
    private static final DateTimeFormatter HUMAN_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' h:mm a");

    @Override
    public boolean isValid(EventsLocationsData data,
                           ConstraintValidatorContext context) {
        if (data == null) return true;

        boolean isValid = true;

        if (data.getEventStartTime() == null) {
            addViolation(context, "eventStartTime", "Please choose a date and" +
                    " time for your performance.");
            isValid = false;
        } else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime minAllowedTime = now.plusHours(MIN_NOTICE_HOURS);

            if (data.getEventStartTime().isBefore(now)) {
                addViolation(context, "eventStartTime", "Events cannot be " +
                        "scheduled in the past.");
                isValid = false;
            } else if (data.getEventStartTime().isBefore(minAllowedTime)) {

                String earliest =
                        DateTimeUtils.formatFriendlyFull(minAllowedTime);
                String message = ("We need at least 48 hours notice. Please " +
                        "pick a time after %s").formatted(earliest);
                addViolation(context, "eventStartTime", message);
                isValid = false;
            }
        }

        if (data.getPrice() == null) {
            addViolation(context, "price", "Please enter a ticket price.");
            isValid = false;
        } else if (data.getPrice().doubleValue() <= 0) {
            addViolation(context, "price", "Ticket price cannot be negative. " +
                    "Please enter 0 or more.");
            isValid = false;
        }

        if (data.getLocationId() <= 0) {
            addViolation(context, "locationId", "Please select a venue for " +
                    "the event.");
            isValid = false;
        }

        if (data.getTickets() <= 0) {
            addViolation(context, "tickets", "Please enter the number of " +
                    "tickets available (minimum 1).");
            isValid = false;
        }

        return isValid;
    }

    private void addViolation(ConstraintValidatorContext context,
                              String property, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
    }
}