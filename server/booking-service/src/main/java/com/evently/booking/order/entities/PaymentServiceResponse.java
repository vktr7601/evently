package com.evently.booking.order.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentServiceResponse implements Serializable {
    private String transactionId;
    private boolean success;
    private String message;
}