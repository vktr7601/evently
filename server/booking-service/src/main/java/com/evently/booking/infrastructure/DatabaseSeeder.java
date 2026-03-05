package com.evently.booking.infrastructure;

import com.evently.booking.infrastructure.data.PromoCodeSeed;
import com.evently.booking.promoCode.service.PromoCodeService;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import events.ticket.TicketsCreationEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(TicketService ticketService,
                                   PromoCodeService promoCodeService,
                                   ObjectMapper objectMapper) {
        return args -> {
            InputStream inputStream = getClass().getResourceAsStream(
                    "/tickets-creation.json");

            if (inputStream == null) {
                throw new RuntimeException("Could not find tickets-creation" +
                        ".json in resources!");
            }

            List<TicketsCreationEvent> events = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<TicketsCreationEvent>>() {}
            );
            ticketService.addTickets(events);


            InputStream promoCodesJson = getClass().getResourceAsStream(
                    "/promo-codes.json");

            try {
                List<PromoCodeSeed> promoCodeSeeds = objectMapper.readValue(
                        promoCodesJson,
                        new TypeReference<List<PromoCodeSeed>>() {}
                );
                promoCodeService.seedPromoCodes(promoCodeSeeds);
            }
            catch (Exception e) {
                System.out.println();
            }


        };
    }
}