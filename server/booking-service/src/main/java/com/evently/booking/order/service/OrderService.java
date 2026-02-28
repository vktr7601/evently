package com.evently.booking.order.service;

import com.evently.booking.infrastructure.clients.eventsService.EventServiceClient;
import com.evently.booking.infrastructure.clients.paymentService.PaymentServiceClient;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentMapper;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentRequest;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentResponse;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentServiceResponse;
import com.evently.booking.infrastructure.exceptions.*;
import com.evently.booking.order.data.OrderMapper;
import com.evently.booking.order.dto.FinishOrderRequest;
import com.evently.booking.order.dto.OrderDetails;
import com.evently.booking.order.dto.OrderListItemDto;
import com.evently.booking.order.dto.OrderRequest;
import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.order.repository.OrderRepository;
import com.evently.booking.ticket.data.TicketNumberGenerator;
import com.evently.booking.ticket.dto.TicketListItem;
import com.evently.booking.ticket.model.Ticket;
import com.evently.booking.ticket.model.TicketStatus;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import events.order.OrderPaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketService ticketService;
    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;
    private final ObjectMapper objectMapper;
    private final PaymentServiceClient paymentServiceClient;
    private final EventServiceClient eventServiceClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void addTicketsToOrder(long userId, OrderRequest orderRequest) {
        orderRepository.findPendingOrderByIdAndUserId(userId).map(order -> updateExistingOrder(order, orderRequest, userId)).orElseGet(() -> createNewOrder(orderRequest, userId));
    }

    public OrderDetails getActiveUserOrder(Long userId) throws OrderExpiredException {
        Order order =
                orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new OrderExpiredException("Your booking window has " + "timed out. " + "Please start a new order."));

        List<TicketListItem> listItems =
                ticketService.getTicketsByOrderId(order.getId());

        return mapToDto(order, listItems);
    }


    public void cancelActiveOrder(long userId) {
        var order =
                orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));

        updateOrderDetails(order, OrderStatus.CANCELLED);
    }


    @Transactional
    Order createNewOrder(OrderRequest orderRequest, long userId) {
        //check if event location status was not changed during the user were
        // selecting a ticket
        boolean isAvailable =
                eventServiceClient.checkEventLocationsStateById(orderRequest.getEventLocationId()).getBody();
        if (!isAvailable) {
            throw new BookingUnavailableException();
        }
        List<Ticket> tickets =
                ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getEventStartTime());

        int requested = orderRequest.getTicketsCount();

        int available = tickets.size();

        if (available < requested) {
            throw new BookingUnavailableException();
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        assignTicketsToOrder(order, tickets);

        orderRepository.save(order);

        return order;
    }

    @Transactional
    public void updateOrderDetails(Order order, OrderStatus status) {
        if (status == OrderStatus.CANCELLED || status == OrderStatus.EXPIRED) {

            List<TicketListItem> activeListItem =
                    ticketService.getTicketsByOrderId(order.getId());
            activeListItem.forEach(x -> x.setStatus(TicketStatus.CANCELED));

            try {
                String json = objectMapper.writeValueAsString(activeListItem);
                order.setAudit(json);
            } catch (JsonProcessingException e) {
                log.error("Failed to create audit log for order {}",
                        order.getId(), e);
            }

            order.setStatus(status);
            order.setActive(false);

            List<Ticket> tickets = order.getTickets();
            if (tickets != null) {
                for (Ticket ticket : tickets) {
                    ticket.setStatus(TicketStatus.AVAILABLE);
                    ticket.setUserId(null);
                    ticket.setOrder(null);
                }
            }
            //todo: raise an event that order is cancelled or expired
            orderRepository.save(order);
        }
    }

    Order updateExistingOrder(Order order, OrderRequest orderRequest,
                              long userId) {
        var booleanRes =
                eventServiceClient.checkEventLocationsStateById(orderRequest.getEventLocationId()).getBody();
        if (!booleanRes) {
            throw new BookingUnavailableException();
        }


        List<Ticket> existingTickets = order.getTickets();
        Map<Long, List<Ticket>> ticketsByEventLocation =
                existingTickets.stream().filter(ticket -> ticket.getEventStartTime().equals(orderRequest.getEventStartTime()) && ticket.getEventLocationsId().equals(orderRequest.getEventLocationId())).collect(Collectors.groupingBy(Ticket::getEventLocationsId));

        if (ticketsByEventLocation.isEmpty()) {
            if (!booleanRes) {
                throw new BookingUnavailableException();
            }
            List<Ticket> tickets =
                    ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), orderRequest.getTicketsCount(), orderRequest.getEventStartTime());

            int requested = orderRequest.getTicketsCount();

            int available = tickets.size();

            if (available < requested) {
                throw new BookingUnavailableException();
            }
            assignTicketsToOrder(order, tickets);
            return order;
        }
        if (orderRequest.getTicketsCount() > ticketsByEventLocation.get(orderRequest.getEventLocationId()).size()) {
            int diff =
                    orderRequest.getTicketsCount() - ticketsByEventLocation.size();
            List<Ticket> tickets =
                    ticketService.getTicketsForEvent(orderRequest.getEventLocationId(), diff, orderRequest.getEventStartTime());
//todo: this is buggy
            int requested = orderRequest.getTicketsCount();

            int available = tickets.size();

            if (diff < available) {
                throw new BookingUnavailableException();
            }
            assignTicketsToOrder(order, tickets);

        } else {
            int diff =
                    ticketsByEventLocation.get(orderRequest.getEventLocationId()).size() - orderRequest.getTicketsCount();
            List<Ticket> ticketsToRemove =
                    ticketsByEventLocation.get(orderRequest.getEventLocationId()).subList(0, diff);
            for (Ticket ticket : ticketsToRemove) {
                ticket.setStatus(TicketStatus.AVAILABLE);
                ticket.setUserId(null);
                ticket.setOrder(null);
                order.setTotalPrice(order.getTotalPrice().subtract(ticket.getPrice()));
            }
        }

        orderRepository.save(order);

        return order;
    }

    public List<OrderListItemDto> getUserOrders(long userId) {
        return orderRepository.getUserOrders(userId);
    }

    public OrderDetails getOrderDetails(long userId, UUID number) {
        Order order = orderRepository.findByOrderNumberAndUserId(number,
                userId).orElseThrow(() -> new NoActiveOrderException(userId));

        List<TicketListItem> ticketListItems = resolveOrderItems(order);

        return mapToDto(order, ticketListItems);
    }

    public List<TicketListItem> resolveOrderItems(Order order) {
        if (order.getAudit() != null && !order.getAudit().isEmpty()) {
            try {
                return objectMapper.readValue(order.getAudit(),
                        new TypeReference<List<TicketListItem>>() {});
            } catch (JsonProcessingException e) {
                log.error("Failed to parse order audit for order: {}",
                        order.getNumber(), e);
                return Collections.emptyList();
            }
        }

        return ticketService.getTicketsByOrderId(order.getId());
    }

    @Transactional
    void assignTicketsToOrder(Order order, List<Ticket> tickets) {
        BigDecimal batchTotal =
                tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO
                        , BigDecimal::add);

        BigDecimal currentTotal = order.getTotalPrice() != null ?
                order.getTotalPrice() : BigDecimal.ZERO;
        order.setTotalPrice(currentTotal.add(batchTotal));

        tickets.forEach(ticket -> {
            ticket.setUserId(order.getUserId());
            ticket.setStatus(TicketStatus.PENDING_PAYMENT);
            ticket.setNumber(TicketNumberGenerator.generateV7());
            order.addTicket(ticket);
        });
    }

    @Transactional(noRollbackFor = PriceChangedException.class)
    public void completeOrder(String userEmail, Long userId,
                              FinishOrderRequest finishOrderRequest) throws ProcessOrderException, OrderExpiredException {
        Order order =
                orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));

        validateOrderDetails(order);
        PaymentRequest paymentRequest =
                paymentMapper.toPaymentRequest(finishOrderRequest, order,
                        userEmail);

        ResponseEntity<PaymentResponse> response =
                paymentServiceClient.handlePayment(paymentRequest);
        PaymentResponse paymentResponse = response.getBody();
        if (paymentResponse.isSuccess()) {
            handleSuccessfulPayment(order, paymentResponse);
        } else {
            handleFailedPayment(paymentResponse);
        }
    }

    // important for validating whether the date of the events hasn't changed
    // or price of tickets, causing the form to be reloaded to show latest
    // details
    public void validateOrderDetails(Order order) {
        boolean dateChanged = order.getTickets().stream()
                .anyMatch(t -> !t.getEventStartTime().equals(t.getOriginalEventStartTime()));

        BigDecimal latestTotal = order.getTickets().stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        boolean priceChanged =
                latestTotal.compareTo(order.getTotalPrice()) != 0;


        if (dateChanged || priceChanged) {
            log.info("Order [{}] snapshot invalid — dateChanged={}, " +
                            "priceChanged={}, latestTotal={}",
                    order.getId(), dateChanged, priceChanged, latestTotal);

            order.setTotalPrice(latestTotal);
            order.getTickets().forEach(t -> t.setOriginalEventStartTime(t.getEventStartTime()));
            orderRepository.save(order);

            String reason = dateChanged
                    ? "The event time has changed. Please confirm the new " +
                    "details."
                    : "The price has been updated. Please refresh the page.";

            throw new PriceChangedException(reason);
        }
    }

    private void handleSuccessfulPayment(Order order,
                                         PaymentResponse paymentResponse) {
        log.info("Payment succeeded for order [{}], transactionId={}",
                order.getId(), paymentResponse.getTransactionId());

        ticketService.finalizeOrder(order.getId());

        order.setStatus(OrderStatus.CONFIRMED);
        order.setActive(false);
        order.setTotalPrice(order.getTickets().stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setReceiptUrl(paymentResponse.getReceiptUrl());
        order.setTransactionId(paymentResponse.getTransactionId());
        orderRepository.save(order);

        OrderPaymentSucceededEvent event =
                orderMapper.toOrderPaymentSucceededEvent(order);
        eventPublisher.publishEvent(event);
    }

    private void handleFailedPayment(PaymentResponse payment) {
        log.warn("Payment failed for order, message={}", payment.getMessage());

//        if (payment.getMessage().startsWith("Invalid card")) {
//            throw new ProcessOrderException(payment);
//        }
//
//        throw new ProcessOrderException(payment);
    }

    public boolean isWithinRefundPeriod(Long userId, UUID number) {
        Order order = orderRepository.findByOrderNumberAndUserId(number,
                userId).orElseThrow(() -> new NoActiveOrderException(userId));


        return order.getTickets().stream().allMatch(ticket -> {
            LocalDateTime refundDeadline =
                    ticket.getEventStartTime().minusDays(1);
            return LocalDateTime.now().isBefore(refundDeadline);
        });
    }


    public Order findByOrderNumberAndUserId(UUID number, Long userId) {
        return orderRepository.findByOrderNumberAndUserId(number, userId).orElseThrow(() -> new NoActiveOrderException(userId));
    }

    OrderDetails mapToDto(Order order, List<TicketListItem> listItems) {
        OrderDetails orderDto = orderMapper.toDto(order, listItems);

        BigDecimal totalSum =
                listItems.stream().map(TicketListItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        orderDto.setTotalPrice(totalSum);

        return orderDto;
    }

    public List<OrderListItemDto> getSystemOrders() {
        return orderRepository.getAllOrders();
    }
}