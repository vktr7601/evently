package com.evently.booking.order;

import com.evently.booking.ticket.entities.TicketListItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyDataGenerator {

    public static List<TicketListItem> generateDummyTickets(int count) {
        List<TicketListItem> tickets = new ArrayList<>();
        Random random = new Random();

        String[] events = {"Molec: Summer Night", "Techno Basement", "Jazz in the Park", "Opera Under the Stars"};
        String[] locations = {"Ancient Theatre, Plovdiv", "Sofia Live Club", "Sea Garden, Varna", "Roman Stadium"};

        for (int i = 0; i < count; i++) {
            long id = i + 1;
            // Generate a 13-digit random number similar to your example
            long ticketNumber = 1771000000000L + (long) (random.nextDouble() * 1000000000L);

            String event = events[random.nextInt(events.length)];
            String location = locations[random.nextInt(locations.length)];

            // Generate a date in the future
            LocalDateTime date = LocalDateTime.now()
                .plusMonths(random.nextInt(6))
                .plusDays(random.nextInt(30))
                .withHour(20)
                .withMinute(0);

            BigDecimal price = BigDecimal.valueOf(45.50 + (random.nextDouble() * 100)).setScale(2, BigDecimal.ROUND_HALF_UP);

            tickets.add(new TicketListItem(id, ticketNumber, event, location, date, price));
        }

        return tickets;
    }
}