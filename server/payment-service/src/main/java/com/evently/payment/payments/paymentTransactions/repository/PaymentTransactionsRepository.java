package com.evently.payment.payments.paymentTransactions.repository;

import com.evently.payment.payments.paymentTransactions.model.PaymentTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTransactionsRepository extends JpaRepository<PaymentTransactions, Long> {
}