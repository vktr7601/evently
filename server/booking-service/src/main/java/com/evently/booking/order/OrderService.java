package com.evently.booking.order;

import com.evently.booking.order.entities.EventsLocationsDto;
import com.evently.booking.order.entities.OrderDto;
import com.evently.booking.order.entities.OrderRequest;
import com.evently.booking.ticket.Ticket;
import com.evently.booking.ticket.TicketService;
import com.evently.booking.ticket.entities.TicketStatus;
import com.evently.booking.ticket.entities.TicketsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketService ticketService;
    private final EventsLocationsClient eventsLocationsClient;

    @Transactional
    public Order createOrder(long userId, OrderRequest orderRequest) {

        Optional<Order> activeOrder = orderRepository.findPendingOrderByIdAndUserId(userId);
        if (activeOrder.isPresent()) {
            List<Ticket> ticketList = ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getDateTime());

            Order order = activeOrder.get();

            for (int i = 0; i < ticketList.size(); i++) {
                Ticket ticket = ticketList.get(i);
                ticket.setUserId(userId);
                ticket.setStatus(TicketStatus.PENDING_PAYMENT);
                ticket.setReservedUntil(order.getExpirationTime());
                BigDecimal updatedPrice = order.getTotalPrice().add(ticket.getPrice());
                order.setTotalPrice(updatedPrice);
                order.addTicket(ticket);
            }

            orderRepository.save(order);
            return order;
        }


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

        order.setTotalPrice(price);

        orderRepository.save(order);

        return order;
    }

    public OrderDto getOrderDetails(long userId, long orderId) {
        Order order = orderRepository.findOrderIfOwnedByUser(userId, orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        List<Ticket> tickets = order.getTickets();
        List<Long> eventLocationsIds = tickets.stream().map(Ticket::getEventLocationsId).toList();
        Map<Long, EventsLocationsDto> locations = eventsLocationsClient.getLocations(eventLocationsIds);
        List<TicketsDto> ticketsDtos = new ArrayList<>();
        OrderDto orderDto = new OrderDto();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Ticket ticket : tickets) {
            EventsLocationsDto eventsLocationsDto = locations.get(ticket.getEventLocationsId());
            TicketsDto ticketsDto = new TicketsDto();
            ticketsDto.setId(ticket.getId());
            ticketsDto.setEventName(eventsLocationsDto.getEventName());
            ticketsDto.setEventLocationId(eventsLocationsDto.getEventLocationId());
            ticketsDto.setEventDate(eventsLocationsDto.getEventStartTime());
            ticketsDto.setTicketPrice(ticket.getPrice());
            totalPrice = totalPrice.add(ticketsDto.getTicketPrice());
            ticketsDtos.add(ticketsDto);
        }

        orderDto.setId(order.getId());
        orderDto.setNumber(order.getNumber());
        orderDto.setExpirationTime(order.getExpirationTime());
        orderDto.setTicket(ticketsDtos);
        orderDto.setTotalPrice(totalPrice);
        return orderDto;
    }


    public OrderDto getActiveUserOrder(Long userId) {
        var order = orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new RuntimeException("Order not found"));
        List<Ticket> tickets = order.getTickets();
        List<Long> eventLocationsIds = tickets.stream().map(Ticket::getEventLocationsId).toList();
        Map<Long, EventsLocationsDto> locations = eventsLocationsClient.getLocations(eventLocationsIds);
        List<TicketsDto> ticketsDtos = new ArrayList<>();
        OrderDto orderDto = new OrderDto();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Ticket ticket : tickets) {
            EventsLocationsDto eventsLocationsDto = locations.get(ticket.getEventLocationsId());
            TicketsDto ticketsDto = new TicketsDto();
            ticketsDto.setId(ticket.getId());
            ticketsDto.setEventName(eventsLocationsDto.getEventName());
            ticketsDto.setEventLocationId(eventsLocationsDto.getEventLocationId());
            ticketsDto.setEventDate(eventsLocationsDto.getEventStartTime());
            ticketsDto.setTicketPrice(ticket.getPrice());
            totalPrice = totalPrice.add(ticketsDto.getTicketPrice());
            ticketsDtos.add(ticketsDto);
        }

        orderDto.setId(order.getId());
        orderDto.setNumber(order.getNumber());
        orderDto.setExpirationTime(order.getExpirationTime());
        orderDto.setTicket(ticketsDtos);
        orderDto.setTotalPrice(totalPrice);
        return orderDto;
    }

    @Transactional
    public void expireActiveUserOrder(Long userId) {
        // 1. Fix the ID parameters
        var order = orderRepository.findPendingOrderByIdAndUserId(userId)
            .orElseThrow(() -> new RuntimeException("Order not found or does not belong to the user"));

        // 2. Update Order status
        order.setStatus(OrderStatus.CANCELLED);
        order.setActive(false);

        // 3. Release the tickets
        List<Ticket> tickets = order.getTickets();
        for (Ticket ticket : tickets) {
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setUserId(null);
            ticket.setReservedUntil(null);
            ticket.setOrder(null);
        }

        orderRepository.save(order); // Handled automatically if using @Transactional
    }
}