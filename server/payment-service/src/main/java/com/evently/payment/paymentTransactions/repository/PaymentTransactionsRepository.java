package com.evently.payment.paymentTransactions.repository;

import com.evently.payment.paymentTransactions.model.PaymentTransaction;
import com.evently.payment.paymentTransactions.model.PaymentTransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTransactionsRepository extends JpaRepository<PaymentTransaction, Long> {
    List<PaymentTransaction> findByIdAndUserId(Long id, long userId);

    List<PaymentTransaction> findAllByUserId(long userId);

    @Modifying
    @Query("UPDATE PaymentTransaction p SET p.paymentTransactionStatus = " +
            ":status WHERE p.id = :id")
    void updateStatus(@Param("id") Long id,
                      @Param("status") PaymentTransactionStatus status);
}