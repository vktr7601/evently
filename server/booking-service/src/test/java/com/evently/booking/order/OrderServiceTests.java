package com.evently.booking.order;

import com.evently.booking.ticket.TicketService;
import com.evently.booking.ticket.entities.TicketListItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TicketService ticketRepository;

//    @Autowired
//    private final ObjectMapper objectMapper;

    @Test
    public void testObjectMapper() throws JsonProcessingException {

//        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
//        objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        List<TicketListItem> ticketListItems = DummyDataGenerator.generateDummyTickets(2);
//        try {
//            var file = objectMapper.writeValueAsString(ticketListItems);
//            List<TicketListItem> list = objectMapper.readValue(file, List.class);
//            System.out.println();
//        } catch (JsonProcessingException e) {
//            System.out.println();
//            e.printStackTrace();
//        }
//
//        System.out.println();
    }
}