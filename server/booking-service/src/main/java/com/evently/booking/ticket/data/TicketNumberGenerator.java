package com.evently.booking.ticket.data;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class TicketNumberGenerator {
    public static UUID generateV7() {
        return Generators.timeBasedEpochGenerator().generate();
    }
}