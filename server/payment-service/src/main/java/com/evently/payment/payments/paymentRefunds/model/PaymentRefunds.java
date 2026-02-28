package com.evently.payment.payments.paymentRefunds.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "payment_refunds")
public class PaymentRefunds extends BaseEntity {

//    CREATE TABLE payment_refunds (
//            id                  UUID PRIMARY KEY,
//            transaction_id      UUID NOT NULL REFERENCES
//            payment_transactions(id),
//    customer_id         UUID NOT NULL,
//    provider            VARCHAR(50) NOT NULL,
//    provider_refund_id  VARCHAR(255),             -- Stripe refund ID e.g.
//    re_xxxxx
//    amount              BIGINT NOT NULL,           -- allows partial refunds
//    currency            VARCHAR(10) NOT NULL,
//    status              VARCHAR(50) NOT NULL,      -- PENDING, SUCCEEDED,
//    FAILED
//    reason              VARCHAR(100),              -- DUPLICATE,
//    FRAUDULENT, CUSTOMER_REQUEST
//    requested_by        UUID,                      -- staff or system user
//    who triggered it
//    requested_at        TIMESTAMP NOT NULL,
//    processed_at        TIMESTAMP,                 -- null until provider
//    confirms
//    created_at          TIMESTAMP NOT NULL,
//    updated_at          TIMESTAMP NOT NULL
//);
}