package com.evently.booking.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.tickets WHERE o.id = :orderId AND o.userId = :userId")
    Optional<Order> findOrderIfOwnedByUser(
        @Param("orderId") Long orderId,
        @Param("userId") Long userId
    );
}