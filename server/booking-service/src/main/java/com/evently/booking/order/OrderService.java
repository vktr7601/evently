package com.evently.booking.order;

import com.evently.booking.exceptions.NoActiveOrderException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketService ticketService;
    private final EventsLocationsClient eventsLocationsClient;

    @Transactional
    public Order addTicketsToOrder(long userId, OrderRequest orderRequest) {
        return orderRepository.findPendingOrderByIdAndUserId(userId)
            .map(order -> updateExistingOrder(order, orderRequest, userId))
            .orElseGet(() -> createNewOrder(orderRequest, userId));
//        if (activeOrder.isPresent()) {
//            List<Ticket> ticketList = ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getDateTime());
//
//            Order order = activeOrder.get();
//
//            for (Ticket ticket : ticketList) {
//                ticket.setUserId(userId);
//                ticket.setStatus(TicketStatus.PENDING_PAYMENT);
//                ticket.setReservedUntil(order.getExpirationTime());
//                BigDecimal updatedPrice = order.getTotalPrice().add(ticket.getPrice());
//                order.setTotalPrice(updatedPrice);
//                order.addTicket(ticket);
//            }
//
//            orderRepository.save(order);
//            return order;
//        }
//
//
//        List<Ticket> ticketList = ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getDateTime());
//
//        Order order = new Order();
//        order.setUserId(1l);
//        order.setStatus(OrderStatus.PENDING_PAYMENT);
//        BigDecimal price = BigDecimal.ZERO;
//        for (int i = 0; i < ticketList.size(); i++) {
//            Ticket ticket = ticketList.get(i);
//            ticket.setUserId(1l);
//            ticket.setStatus(TicketStatus.PENDING_PAYMENT);
//            ticket.setReservedUntil(LocalDateTime.now().plusMinutes(10));
//            price = price.add(ticket.getPrice());
//            order.addTicket(ticket);
//        }
//
//        order.setTotalPrice(price);
//
//        orderRepository.save(order);
//
//        return order;
    }

//    public OrderDto getOrderDetails(long userId, long orderId) {
//        Order order = orderRepository.findOrderIfOwnedByUser(userId, orderId).orElseThrow(() -> new NoActiveOrderException(userId));
//        List<Ticket> tickets = order.getTickets();
//        List<Long> eventLocationsIds = tickets.stream().map(Ticket::getEventLocationsId).toList();
//        Map<Long, EventsLocationsDto> locations = eventsLocationsClient.getLocations(eventLocationsIds);
//        List<TicketsDto> ticketsDtos = new ArrayList<>();
//        OrderDto orderDto = new OrderDto();
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        for (Ticket ticket : tickets) {
//            EventsLocationsDto eventsLocationsDto = locations.get(ticket.getEventLocationsId());
//            TicketsDto ticketsDto = new TicketsDto();
//            ticketsDto.setId(ticket.getId());
//            ticketsDto.setEventName(eventsLocationsDto.getEventName());
//            ticketsDto.setEventLocationId(eventsLocationsDto.getEventLocationId());
//            ticketsDto.setEventDate(eventsLocationsDto.getEventStartTime());
//            ticketsDto.setTicketPrice(ticket.getPrice());
//            totalPrice = totalPrice.add(ticketsDto.getTicketPrice());
//            ticketsDtos.add(ticketsDto);
//        }
//
//        orderDto.setId(order.getId());
//        orderDto.setNumber(order.getNumber());
//        orderDto.setExpirationTime(order.getExpirationTime());
//        orderDto.setTicket(ticketsDtos);
//        orderDto.setTotalPrice(totalPrice);
//        return orderDto;
//    }


    public OrderDto getActiveUserOrder(Long userId) {

        var order = orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));
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

    public void cancelActiveOrder(long userId) {
        var order = orderRepository.findPendingOrderByIdAndUserId(userId)
            .orElseThrow(() -> new NoActiveOrderException(userId));

        updateOrderDetails(order, OrderStatus.CANCELLED);
    }

    @Transactional
    public void expireActiveUserOrder(Long userId) {
        // 1. Fix the ID parameters
        var order = orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new RuntimeException("Order not found or does not belong to the user"));
        updateOrderDetails(order, OrderStatus.EXPIRED);
        // 2. Update Order status
//        order.setStatus(OrderStatus.CANCELLED);
//        order.setActive(false);
//
//        // 3. Release the tickets
//        List<Ticket> tickets = order.getTickets();
//        for (Ticket ticket : tickets) {
//            ticket.setStatus(TicketStatus.AVAILABLE);
//            ticket.setUserId(null);
//            ticket.setReservedUntil(null);
//            ticket.setOrder(null);
//        }
//
//        orderRepository.save(order); // Handled automatically if using @Transactional
    }

    Order createNewOrder(OrderRequest orderRequest, long userId) {
        List<Ticket> ticketList = ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getDateTime());
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        BigDecimal price = BigDecimal.ZERO;
        for (Ticket ticket : ticketList) {
            ticket.setUserId(userId);
            ticket.setStatus(TicketStatus.PENDING_PAYMENT);
            ticket.setReservedUntil(order.getExpirationTime());
            price = price.add(ticket.getPrice());
            order.addTicket(ticket);
        }

        order.setTotalPrice(price);

        orderRepository.save(order);

        return order;
    }

    public void updateOrderDetails(Order order, OrderStatus status) {
        if (status == OrderStatus.CANCELLED) {
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

            orderRepository.save(order);
        }
        if (status == OrderStatus.EXPIRED) {
            order.setStatus(OrderStatus.EXPIRED);
            order.setActive(false);
            List<Ticket> tickets = order.getTickets();
            for (Ticket ticket : tickets) {
                ticket.setStatus(TicketStatus.AVAILABLE);
                ticket.setUserId(null);
                ticket.setReservedUntil(null);
                ticket.setOrder(null);
            }
            orderRepository.save(order);
        }
    }

    Order updateExistingOrder(Order order, OrderRequest orderRequest, long userId) {
        List<Ticket> ticketList = ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getDateTime());

        for (Ticket ticket : ticketList) {
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
}