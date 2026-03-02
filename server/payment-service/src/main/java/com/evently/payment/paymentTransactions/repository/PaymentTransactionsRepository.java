package com.evently.payment.paymentTransactions.repository;

import com.evently.payment.paymentTransactions.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTransactionsRepository extends JpaRepository<PaymentTransaction, Long> {
    Optional<PaymentTransaction> findByIdAndUserId(Long id, Long userId);
}