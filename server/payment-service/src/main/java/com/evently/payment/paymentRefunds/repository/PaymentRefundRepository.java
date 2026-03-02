package com.evently.payment.paymentRefunds.repository;

import com.evently.payment.paymentRefunds.model.PaymentRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRefundRepository extends JpaRepository<PaymentRefund, Long> {
}