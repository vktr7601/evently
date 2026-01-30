package com.company.ticket_service.checkout;

import com.company.ticket_service.event.Event;
import com.company.ticket_service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final PaymentService paymentService;


    @GetMapping("/{id}")
    public Event event(@PathVariable long id) {
        return null;
    }


    @PostMapping
    public void checkout(@RequestBody CheckoutRequest checkoutRequest) {
    }
}
