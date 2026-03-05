package com.evently.booking.order.service;

import com.evently.booking.infrastructure.clients.eventsService.EventServiceClient;
import com.evently.booking.infrastructure.clients.paymentService.PaymentServiceClient;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentMapper;
import com.evently.booking.infrastructure.exceptions.*;
import com.evently.booking.order.data.OrderMapper;
import com.evently.booking.order.dto.FinishOrderRequest;
import com.evently.booking.order.dto.OrderDetails;
import com.evently.booking.order.dto.OrderListItemDto;
import com.evently.booking.order.dto.OrderRequest;
import com.evently.booking.order.model.Order;
import com.evently.booking.order.model.OrderStatus;
import com.evently.booking.order.repository.OrderRepository;
import com.evently.booking.order.service.orderUpdate.OrderUpdateManager;
import com.evently.booking.promoCode.service.PromoCodeService;
import com.evently.booking.ticket.dto.TicketListItem;
import com.evently.booking.ticket.model.Ticket;
import com.evently.booking.ticket.model.TicketStatus;
import com.evently.booking.ticket.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.payment.payment.PaymentRequest;
import dto.payment.payment.PaymentResponse;
import events.order.OrderPaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final OrderUpdateManager orderUpdateManager;
    private final PromoCodeService promoCodeService;

    @Transactional
    public void addTicketsToOrder(long userId, OrderRequest orderRequest) {
        orderRepository.findPendingOrderByIdAndUserId(userId).map(order -> updateExistingOrder(order, orderRequest, userId)).orElseGet(() -> createNewOrder(orderRequest, userId));
    }

    @Transactional(readOnly = true)
    public OrderDetails getActiveUserOrder(Long userId) throws OrderExpiredException {
        Order order =
                orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new OrderExpiredException("Your booking window has " + "timed out. " + "Please start a new order."));

        List<TicketListItem> listItems =
                ticketService.getTicketsByOrder(order);


        return orderMapper.toDto(order, listItems);
    }

    @Transactional
    public OrderDetails releaseTickets(Long userId, OrderRequest orderRequest) {
        Order order = orderRepository.findPendingOrderByIdAndUserId(userId)
                .orElseThrow(() -> new NoActiveOrderException(userId));

        List<Ticket> ticketsToRelease = order.getTickets().stream()
                .filter(t -> t.getEventLocationsId().equals(orderRequest.getEventLocationId())
                        && t.getEventStartTime().equals(orderRequest.getEventStartTime()))
                .limit(orderRequest.getTicketsCount())
                .toList();

        for (Ticket ticket : ticketsToRelease) {
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setUserId(null);
            ticket.setOrder(null);
            order.setTotalPrice(order.getTotalPrice().subtract(ticket.getPrice()));
        }
        order.getTickets().removeAll(ticketsToRelease);

        List<TicketListItem> listItems;
        if (order.getTickets().isEmpty()) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setActive(false);
            listItems = Collections.emptyList();
        } else {
            listItems = ticketService.getTicketsByOrder(order);
        }

        orderRepository.save(order);
        return orderMapper.toDto(order, listItems);
    }

    @Transactional
    public void cancelActiveOrder(long userId) {
        var order =
                orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));

        updateOrder(order, OrderStatus.CANCELLED);
    }


    @Transactional
    Order createNewOrder(OrderRequest orderRequest, long userId) {
        //check if event location status was not changed during the user were
        // selecting a ticket
        var isAvailable =
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

    public void updateOrder(Order order, OrderStatus orderStatus) {
        orderUpdateManager.update(order, orderStatus);
    }

    public void updateOrderDetails(Order order, OrderStatus status) {
        if (status == OrderStatus.CANCELLED || status == OrderStatus.EXPIRED) {

            List<TicketListItem> activeListItem =
                    ticketService.getTicketsByOrder(order);
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

            orderRepository.save(order);
        }
        if (status == OrderStatus.REFUNDED) {
            List<TicketListItem> activeListItem =
                    ticketService.getTicketsByOrder(order);
            activeListItem.forEach(x -> x.setStatus(TicketStatus.REFUNDED));

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
        return orderMapper.toDto(order, ticketListItems);
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

        return ticketService.getTicketsByOrder(order);
    }

    @Transactional
    void assignTicketsToOrder(Order order, List<Ticket> tickets) {
        BigDecimal batchTotal =
                tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO
                        , BigDecimal::add);

        BigDecimal currentTotal = order.getTotalPrice() != null ?
                order.getTotalPrice() : BigDecimal.ZERO;
        order.setTotalPrice(currentTotal.add(batchTotal));

        updateOrderDetails(order, OrderStatus.PENDING_PAYMENT);
        tickets.forEach(ticket -> {
            ticket.setUserId(order.getUserId());
            ticket.setStatus(TicketStatus.PENDING_PAYMENT);
            order.addTicket(ticket);
        });
    }

    @Transactional(noRollbackFor = PriceChangedException.class)
    public void completeOrder(String userEmail, Long userId,
                              FinishOrderRequest finishOrderRequest) throws ProcessOrderException, OrderExpiredException {
        Order order =
                orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));

        validateOrderDetails(order);
        if (finishOrderRequest.getPromoCode() != null) {
            promoCodeService.validatePromoCode(finishOrderRequest.getPromoCode(), userId);
            promoCodeService.validateAmount(order.getTotalPrice(),
                    finishOrderRequest.getPromoCode());

            BigDecimal bigDecimal =
                    promoCodeService.applyPromoCode(order.getTotalPrice(),
                            finishOrderRequest.getPromoCode());

            order.setTotalPrice(bigDecimal);
        }

        PaymentRequest paymentRequest =
                paymentMapper.toPaymentRequest(finishOrderRequest, order,
                        userEmail);

        ResponseEntity<PaymentResponse> response =
                paymentServiceClient.processPayment(paymentRequest);
        PaymentResponse paymentResponse = response.getBody();
        if (paymentResponse.isSuccess()) {
            handleSuccessfulPayment(order, paymentResponse);
            if (finishOrderRequest.getPromoCode() != null) {
                promoCodeService.updatePromoCode(finishOrderRequest.getPromoCode());
            }
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
        order.setTransactionId(paymentResponse.getTransactionId());
        updateOrder(order, OrderStatus.CONFIRMED);
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

    public Order findByOrderNumberAndUserId(UUID number, Long userId) {
        return orderRepository.findByOrderNumberAndUserId(number, userId).orElseThrow(() -> new NoActiveOrderException(userId));
    }

    public List<OrderListItemDto> getSystemOrders() {
        return orderRepository.getAllOrders();
    }

    public List<TicketListItem> resolveUserTickets(long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .flatMap(order -> resolveOrderItems(order).stream())
                .toList();
    }
}