package com.evently.booking.order;

import com.evently.booking.exceptions.NoActiveOrderException;
import com.evently.booking.exceptions.OrderNotRefundableException;
import com.evently.booking.exceptions.ProcessOrderException;
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
import java.time.LocalDateTime;
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
        orderRepository.findPendingOrderByIdAndUserId(userId)
            .map(order -> updateExistingOrder(order, orderRequest, userId))
            .orElseGet(() -> createNewOrder(orderRequest, userId));
    }

    public OrderDetailsDto getActiveUserOrder(Long userId) {
        Order order = orderRepository.findPendingOrderByIdAndUserId(userId)
            .orElseThrow(() -> new NoActiveOrderException(userId));

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

    Order createNewOrder(OrderRequest orderRequest, long userId) {
        List<Ticket> tickets = ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getDateTime());

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        assignTicketsToOrder(order, tickets);

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
                ticket.setOrder(null);
            }
            orderRepository.save(order);
        }
    }

    Order updateExistingOrder(Order order, OrderRequest orderRequest, long userId) {
        List<Ticket> ticketList = ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getDateTime());

        assignTicketsToOrder(order, ticketList);
//        for (Ticket ticket : ticketList) {
//            ticket.setUserId(userId);
//            ticket.setStatus(TicketStatus.PENDING_PAYMENT);
//            BigDecimal updatedPrice = order.getTotalPrice().add(ticket.getPrice());
//            order.setTotalPrice(updatedPrice);
//            order.addTicket(ticket);
//        }

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

    public List<TicketListItem> resolveOrderItems(Order order) {
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

    void assignTicketsToOrder(Order order, List<Ticket> tickets) {
        tickets.forEach(ticket -> {
            ticket.setUserId(order.getUserId());
            ticket.setStatus(TicketStatus.PENDING_PAYMENT);
            order.setTotalPrice(order.getTotalPrice().add(ticket.getPrice()));
            order.addTicket(ticket);
        });
    }

    public void finishActiveUserOrder(Long userId, FinishOrderRequest finishOrderRequest) throws ProcessOrderException {
        Order order = orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(order.getTotalPrice());
        paymentRequest.setCardNumber(finishOrderRequest.getCardNumber().trim());
        paymentRequest.setCardExpiry(finishOrderRequest.getCardExpiry().trim());
        paymentRequest.setCardCvv(finishOrderRequest.getCardCvv().trim());
        paymentRequest.setUserId(userId);
        paymentRequest.setOrderId(order.getId());

        ResponseEntity<PaymentServiceResponse> response = paymentClient.processPayment(paymentRequest);
        if (response.getBody().isSuccess()) {
            ticketService.finalizeOrder(order.getId());
            order.setStatus(OrderStatus.CONFIRMED);
            order.setActive(false);
            order.setTransactionId(response.getBody().toString());
            orderRepository.save(order);
        } else {
            throw new ProcessOrderException(response.getBody());
        }
    }

    @Transactional
    public void refundOrder(long number, long userId) throws OrderNotRefundableException {
        if (!isOrderRefundable(userId, number)) {
            throw new OrderNotRefundableException(number);
        }


        Order order = orderRepository.findByOrderNumberAndUserId(number, userId).orElseThrow(
            () -> new NoActiveOrderException(userId));

        List<Ticket> tickets = order.getTickets();

        List<TicketListItem> ticketListItems = resolveOrderItems(order);
        for (TicketListItem item : ticketListItems) {
            item.setStatus(TicketStatus.REFUNDED);
        }


        try {
            String json = objectMapper.writeValueAsString(ticketListItems);
            order.setAudit(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setAmount(order.getTotalPrice());
        refundRequest.setTransactionId(order.getTransactionId());
        ResponseEntity<PaymentServiceResponse> response = paymentClient.processRefund(refundRequest);
        if (response.getBody().isSuccess()) {
            for (Ticket ticket : tickets) {
                ticket.setStatus(TicketStatus.AVAILABLE);
                ticket.setUserId(null);
                ticket.setOrder(null);
            }
            order.setStatus(OrderStatus.REFUNDED);
            order.setTransactionId(response.getBody().toString());
            orderRepository.save(order);
        } else {
            throw new ProcessOrderException(response.getBody());
        }
        System.out.println();

    }

    public boolean isOrderRefundable(Long userId, Long number) {
        Order order = orderRepository.findByOrderNumberAndUserId(number, userId).orElseThrow(
            () -> new NoActiveOrderException(userId));

        List<Ticket> tickets = order.getTickets();

        boolean canRefund = true;
        for (Ticket ticket : tickets) {
            if (ticket.getDateTime().isBefore(LocalDateTime.now().plusHours(2))) {
                canRefund = false;
                break;
            }
        }
        return canRefund;
    }


    public Order findByOrderNumberAndUserId(Long number, Long userId) {
        return orderRepository.findByOrderNumberAndUserId(number, userId).orElseThrow(
            () -> new NoActiveOrderException(userId));
    }

    public void save(Order order) {
        orderRepository.save(order);
    }


    OrderDetailsDto mapToDto(Order order, List<TicketListItem> listItems) {
        OrderDetailsDto orderDto = orderMapper.toDto(order, listItems);

        BigDecimal totalSum = listItems.stream().map(TicketListItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        orderDto.setTotalPrice(totalSum);

        return orderDto;
    }
}