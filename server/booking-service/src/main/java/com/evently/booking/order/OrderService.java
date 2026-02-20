package com.evently.booking.order;

import com.evently.booking.infrastructure.clients.eventsService.EventServiceClient;
import com.evently.booking.infrastructure.clients.paymentService.PaymentServiceClient;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentServiceRequest;
import com.evently.booking.infrastructure.clients.paymentService.data.PaymentServiceResponse;
import com.evently.booking.infrastructure.exceptions.BookingUnavailableException;
import com.evently.booking.infrastructure.exceptions.NoActiveOrderException;
import com.evently.booking.infrastructure.exceptions.OrderNotRefundableException;
import com.evently.booking.infrastructure.exceptions.ProcessOrderException;
import com.evently.booking.order.data.OrderMapper;
import com.evently.booking.order.data.OrderStatus;
import com.evently.booking.order.data.OrdersMapper;
import com.evently.booking.order.entities.*;
import com.evently.booking.ticket.Ticket;
import com.evently.booking.ticket.TicketService;
import com.evently.booking.ticket.data.TicketStatus;
import com.evently.booking.ticket.entities.TicketListItem;
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
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketService ticketService;
    //    private final OrderMapper orderMapper;
    private final OrdersMapper ordersMapper;
    private final ObjectMapper objectMapper;
    private final PaymentServiceClient paymentServiceClient;
    private final EventServiceClient eventServiceClient;

    @Transactional
    public void addTicketsToOrder(long userId, OrderRequest orderRequest) {
        orderRepository.findPendingOrderByIdAndUserId(userId)
                .map(order -> updateExistingOrder(order, orderRequest, userId))
                .orElseGet(() -> createNewOrder(orderRequest, userId));
    }

    public OrderDetails getActiveUserOrder(Long userId) {
        Order order = orderRepository.findPendingOrderByIdAndUserId(userId)
                .orElseThrow(() -> new NoActiveOrderException(userId));

        List<TicketListItem> listItems =
                ticketService.getTicketsByOrderId(order.getId());

        return mapToDto(order, listItems);
    }

    public void cancelActiveOrder(long userId) {
        var order =
                orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));

        updateOrderDetails(order, OrderStatus.CANCELLED);
    }

    public void finishOrder(long userId, long orderId, String transactionId) {
        var order = orderRepository.findPendingOrderByIdAndUserId(userId);
    }

    @Transactional
    Order createNewOrder(OrderRequest orderRequest, long userId) {
        if (!eventServiceClient.checkEventLocationsStateById(orderRequest.getEventLocationId())) {
            throw new BookingUnavailableException();
        }
        List<Ticket> tickets = ticketService.getTicketsForEvent(
                orderRequest.getEventLocationId(),
                orderRequest.getTicketsCount()
                , orderRequest.getEventStartTime());

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

    public void updateOrderDetails(Order order, OrderStatus status) {
        if (status == OrderStatus.CANCELLED) {

            order.setStatus(OrderStatus.CANCELLED);
            order.setActive(false);
            List<TicketListItem> activeListItem =
                    ticketService.getTicketsByOrderId(order.getId());

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
            List<TicketListItem> activeListItem =
                    ticketService.getTicketsByOrderId(order.getId());

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
    }

    Order updateExistingOrder(Order order, OrderRequest orderRequest,
                              long userId) {
        if (!eventServiceClient.checkEventLocationsStateById(orderRequest.getEventLocationId())) {
            throw new BookingUnavailableException();
        }

        List<Ticket> existingTickets = order.getTickets();
        Map<Long, List<Ticket>> ticketsByEventLocation =
                existingTickets.stream()
                        .filter(ticket -> ticket.getEventStartTime().equals(orderRequest.getEventStartTime()) && ticket.getEventLocationsId().equals(orderRequest.getEventLocationId()))
                        .collect(Collectors.groupingBy(Ticket::getEventLocationsId));

        if (ticketsByEventLocation.isEmpty()) {
            if (!eventServiceClient.checkEventLocationsStateById(orderRequest.getEventLocationId())) {
                throw new BookingUnavailableException();
            }
            List<Ticket> tickets = ticketService.getTicketsForEvent(
                    orderRequest.getEventLocationId(),
                    orderRequest.getTicketsCount()
                    , orderRequest.getEventStartTime());

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
            List<Ticket> tickets = ticketService.getTicketsForEvent(
                    orderRequest.getEventLocationId(),
                    diff
                    , orderRequest.getEventStartTime());
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

    List<OrderListItemDto> getUserOrders(long userId) {
        return orderRepository.getUserOrders(userId);
    }

    OrderDetails getOrderDetails(long userId, long number) {
        Order order = orderRepository.findByOrderNumberAndUserId(number,
                userId).orElseThrow(() -> new NoActiveOrderException(userId));

        List<TicketListItem> ticketListItems = resolveOrderItems(order);

        return mapToDto(order, ticketListItems);
    }

    public List<TicketListItem> resolveOrderItems(Order order) {
        if (order.getAudit() != null && !order.getAudit().isEmpty()) {
            try {
                return objectMapper.readValue(order.getAudit(),
                        new TypeReference<List<TicketListItem>>() {
                        });
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
        BigDecimal batchTotal = tickets.stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal currentTotal = order.getTotalPrice() != null ?
                order.getTotalPrice() : BigDecimal.ZERO;
        order.setTotalPrice(currentTotal.add(batchTotal));

        tickets.forEach(ticket -> {
            ticket.setUserId(order.getUserId());
            ticket.setStatus(TicketStatus.PENDING_PAYMENT);

            order.addTicket(ticket);
        });
    }

    public void finishActiveUserOrder(Long userId,
                                      FinishOrderRequest finishOrderRequest) throws ProcessOrderException {
        Order order =
                orderRepository.findPendingOrderByIdAndUserId(userId).orElseThrow(() -> new NoActiveOrderException(userId));

        PaymentServiceRequest paymentServiceRequest =
                new PaymentServiceRequest();
        paymentServiceRequest.setAmount(order.getTotalPrice());
        paymentServiceRequest.setCardNumber(finishOrderRequest.getCardNumber().trim());
        paymentServiceRequest.setCardExpiry(finishOrderRequest.getCardExpiry().trim());
        paymentServiceRequest.setCardCvv(finishOrderRequest.getCardCvv().trim());
        paymentServiceRequest.setUserId(userId);
        paymentServiceRequest.setOrderId(order.getId());

        ResponseEntity<PaymentServiceResponse> response =
                paymentServiceClient.processPayment(paymentServiceRequest);
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


        Order order = orderRepository.findByOrderNumberAndUserId(number,
                userId).orElseThrow(
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
        ResponseEntity<PaymentServiceResponse> response =
                paymentServiceClient.processRefund(refundRequest);
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
        Order order = orderRepository.findByOrderNumberAndUserId(number,
                userId).orElseThrow(
                () -> new NoActiveOrderException(userId));

        List<Ticket> tickets = order.getTickets();

        boolean canRefund = true;
        for (Ticket ticket : tickets) {
            if (ticket.getEventStartTime().isBefore(LocalDateTime.now().plusHours(2))) {
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


    OrderDetails mapToDto(Order order, List<TicketListItem> listItems) {
        OrderDetails orderDto = ordersMapper.toDto(order, listItems);

        BigDecimal totalSum =
                listItems.stream().map(TicketListItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        orderDto.setTotalPrice(totalSum);

        return orderDto;
    }


}