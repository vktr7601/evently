package com.evently.payment.payments.paymentRefunds.repository;

import com.evently.payment.payments.paymentRefunds.model.PaymentRefunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRefundsRepository extends JpaRepository<PaymentRefunds, Long> {
}