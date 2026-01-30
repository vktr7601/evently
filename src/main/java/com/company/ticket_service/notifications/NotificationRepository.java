package com.company.ticket_service.notifications;

import com.company.ticket_service.core.BaseRepository;
import com.company.ticket_service.notifications.entities.NotificationDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends BaseRepository<Notification> {

    @Query("""
            SELECT new com.company.ticket_service.notifications.entities.NotificationDto(n.message, n.isRead)
            FROM Notification  as n
            JOIN Account AS ac  on n.account.id = ac.id
                            where ac.email = :email
            ORDER BY n.createdAt
            """)
    List<NotificationDto> findAllByAccountEmail(@Param("email") String email);
}
