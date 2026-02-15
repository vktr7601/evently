package com.evently.booking.order;

import com.evently.booking.order.entities.OrderDto;
import com.evently.booking.order.entities.OrderRequest;
import com.evently.booking.ticket.Ticket;
import com.evently.booking.ticket.TicketService;
import com.evently.booking.ticket.entities.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketService ticketService;
    private final EventsLocationsClient eventsLocationsClient;

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        List<Ticket> ticketList = ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getDateTime());

        Order order = new Order();
        order.setUserId(1l);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        BigDecimal price = BigDecimal.ZERO;
        for (int i = 0; i < ticketList.size(); i++) {
            Ticket ticket = ticketList.get(i);
            ticket.setUserId(1l);
            ticket.setStatus(TicketStatus.PENDING_PAYMENT);
            ticket.setReservedUntil(LocalDateTime.now().plusMinutes(10));
            price = price.add(ticket.getPrice());
            order.addTicket(ticket);
        }

        orderRepository.save(order);

        return order;
    }

    public OrderDto getOrderDetails(long userId, long orderId) {
        Map<Long, EventsLocationsDto> locations = eventsLocationsClient.getLocations(List.of(1L, 2L));
        Order order = orderRepository.findOrderIfOwnedByUser(userId, orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        List<Ticket> tikcets = order.getTickets();
        List<Long> eventLocationsIds = tikcets.stream().map(Ticket::getEventLocationsId).toList();
        return null;

    }
}