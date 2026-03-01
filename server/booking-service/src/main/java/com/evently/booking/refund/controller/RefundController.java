package com.evently.booking.refund.controller;

import com.evently.booking.infrastructure.clients.paymentService.data.RefundServiceResponse;
import com.evently.booking.refund.dto.OrderRefundEligibility;
import com.evently.booking.refund.dto.RefundEligibility;
import com.evently.booking.refund.service.RefundService;
import constants.ApplicationHeaders;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/refunds")
@AllArgsConstructor
public class RefundController {
    private final RefundService refundService;

    @GetMapping("/orders/{orderNumber}/eligible")
    public ResponseEntity<OrderRefundEligibility> isOrderEligible(
            @RequestHeader(ApplicationHeaders.USER_ID) Long userId,
            @PathVariable UUID orderNumber) {
        OrderRefundEligibility orderRefundEligibility =
                refundService.getOrderRefundEligibility(userId,
                        orderNumber);
        return new ResponseEntity<>(orderRefundEligibility, HttpStatus.OK);
    }

    @GetMapping("/tickets/{ticketId}/eligible")
    public ResponseEntity<RefundEligibility> isTicketEligible(
            @RequestHeader(ApplicationHeaders.USER_ID) Long userId,
            @PathVariable("ticketId") long ticketId) {

        RefundEligibility refundEligibility =
                refundService.getTicketRefundEligibility(ticketId);

        return new ResponseEntity<>(refundEligibility, HttpStatus.OK);
    }

    //    @PostMapping("/orders/{orderNumber}")
//    public ResponseEntity<RefundResponse> refundOrder(
//            @RequestHeader(ApplicationHeaders.USER_ID) Long userId,
//            @PathVariable UUID orderNumber) { ...}
//
    @PostMapping("/tickets/{ticketId}")
    public ResponseEntity<RefundServiceResponse> refundTicket(
            @RequestHeader(ApplicationHeaders.USER_ID) Long userId,
            @PathVariable("ticketId") long ticketId) {
        refundService.refundTicket(ticketId);
        return null;
    }
}