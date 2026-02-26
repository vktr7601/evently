package com.evently.booking.order.repository;

import com.evently.booking.order.dto.OrderListItemDto;
import com.evently.booking.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.tickets WHERE o.id = :orderId " +
            "AND o.userId = :userId")
    Optional<Order> findOrderIfOwnedByUser(
            @Param("orderId") UUID orderId,
            @Param("userId") Long userId
    );

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.tickets WHERE o.userId = " +
            ":userId AND o.status = 'PENDING_PAYMENT'")
    Optional<Order> findPendingOrderByIdAndUserId(@Param("userId") Long userId);

    @Query(value = """
            SELECT new com.evently.booking.order.dto.OrderListItemDto(
               o.number,
               o.totalPrice,
               o.status,
               o.createdAt
            ) FROM Order o
            WHERE o.userId = :userId
            ORDER BY o.createdAt
            """)
    List<OrderListItemDto> getUserOrders(Long userId);


    @Query(value = """
            SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END
            FROM Order o
            WHERE o.userId = :userId and o.id != :excludeOrderId
            """)
    boolean existsActiveOrderForUser(Long userId);


    @Query(value = """
            SELECT o
            FROM Order o
            WHERE o.userId = :userId and o.number=:number
            """)
    Optional<Order> findByOrderNumberAndUserId(
            @Param("number")
            UUID number,
            @Param("userId")
            Long userId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.tickets WHERE o.status = " +
            "'PENDING_PAYMENT' AND o.expirationTime < :now")
    List<Order> findExpiredPendingOrders(@Param("now") LocalDateTime now);

}