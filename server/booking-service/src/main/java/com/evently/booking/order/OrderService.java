package com.evently.booking.order;

import com.evently.booking.exceptions.NoActiveOrderException;
import com.evently.booking.order.entities.*;
import com.evently.booking.ticket.Ticket;
import com.evently.booking.ticket.TicketRepository;
import com.evently.booking.ticket.TicketService;
import com.evently.booking.ticket.entities.TicketListItem;
import com.evently.booking.ticket.entities.TicketStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    public final ObjectMapper objectMapper;
    private final OrderMapper orderMapper;
    private final PaymentClient paymentClient;

    @Transactional
    public void addTicketsToOrder(long userId, OrderRequest orderRequest) {
        orderRepository.findPendingOrderByIdAndUserId(userId).map(order -> updateExistingOrder(order, orderRequest, userId)).orElseGet(() -> createNewOrder(orderRequest, userId));
    }

    public OrderDetailsDto getActiveUserOrder(Long userId) {
        Order order = orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));

        List<TicketListItem> listItems = ticketService.getTicketsByOrderId(order.getId());

        return mapToDto(order, listItems);
    }

    public void cancelActiveOrder(long userId) {
        var order = orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));

        updateOrderDetails(order, OrderStatus.CANCELLED);
    }

    public void finishOrder(long userId, long orderId, String transactionId) {
        var order = orderRepository.findPendingOrderByIdAndUserId(userId);
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
        orderRepository.save(order);

        assignTicketsToOrder(order, ticketList, userId);
//        BigDecimal price = BigDecimal.ZERO;
//        for (Ticket ticket : ticketList) {
//            ticket.setUserId(userId);
//            ticket.setStatus(TicketStatus.PENDING_PAYMENT);
//            ticket.setReservedUntil(order.getExpirationTime());
//            price = price.add(ticket.getPrice());
//            ticket.setOrder(order);
//            // order.addTicket(ticket);
//        }
//
//        order.setTotalPrice(price);
        ticketRepository.saveAllAndFlush(ticketList);

        orderRepository.save(order);

        return order;
    }

    public void updateOrderDetails(Order order, OrderStatus status) {
        if (status == OrderStatus.CANCELLED) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setActive(false);
            List<TicketListItem> activeListItem = ticketService.getTicketsByOrderId(order.getId());

            try {
                String json = objectMapper.writeValueAsString(activeListItem);
                order.setAudit(json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
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

    List<OrderListItemDto> getUserOrders(long userId) {
        return orderRepository.getUserOrders(userId);
    }

    OrderDetailsDto getOrderDetails(long userId, long number) {
        Order order = orderRepository.findByOrderNumberAndUserId(number, userId).orElseThrow(() -> new NoActiveOrderException(userId));

        List<TicketListItem> ticketListItems = resolveOrderItems(order);

        return mapToDto(order, ticketListItems);
    }


    OrderDetailsDto mapToDto(Order order, List<TicketListItem> listItems) {
        OrderDetailsDto orderDto = orderMapper.toDto(order, listItems);

        BigDecimal totalSum = listItems.stream().map(TicketListItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        orderDto.setTotalPrice(totalSum);

        return orderDto;
    }

    List<TicketListItem> resolveOrderItems(Order order) {
        if (order.getAudit() != null && !order.getAudit().isEmpty()) {
            try {
                return objectMapper.readValue(order.getAudit(), new TypeReference<List<TicketListItem>>() {
                });
            } catch (JsonProcessingException e) {
                log.error("Failed to parse order audit for order: {}", order.getNumber(), e);
                return Collections.emptyList();
            }
        }

        return ticketService.getTicketsByOrderId(order.getId());
    }

    void assignTicketsToOrder(Order order, List<Ticket> tickets, Long userId) {
        tickets.forEach(ticket -> {
            ticket.setUserId(userId);
            ticket.setStatus(TicketStatus.PENDING_PAYMENT);
            ticket.setReservedUntil(order.getExpirationTime());
            order.setTotalPrice(order.getTotalPrice().add(ticket.getPrice()));
            order.addTicket(ticket);
        });
    }

    public void finishActiveUserOrder(Long userId, FinishOrderRequest finishOrderRequest) {
        Order order = orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));
//        var totalPrice = activeOrder.getTotalPrice();
//        if (!finishOrderRequest.getPromoCode().isEmpty()) {
//
//        }

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(order.getTotalPrice());
        paymentRequest.setCardNumber(finishOrderRequest.getCardNumber());
        paymentRequest.setCardExpiry(finishOrderRequest.getCardExpiry());
        paymentRequest.setCardCvv(finishOrderRequest.getCardCvv());
        paymentRequest.setUserId(userId);
        paymentRequest.setOrderId(order.getId());

        ResponseEntity<?> response = paymentClient.processPayment(paymentRequest);
        if (response.getStatusCode().is2xxSuccessful()) {
            ticketService.finalizeOrder(order.getId());
            order.setStatus(OrderStatus.CONFIRMED);
            order.setActive(false);
            order.setTransactionId(response.getBody().toString());
            orderRepository.save(order);
            System.out.println();
        }
    }
}