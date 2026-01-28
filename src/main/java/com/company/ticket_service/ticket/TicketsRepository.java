package com.company.ticket_service.ticket;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsRepository extends BaseRepository<Ticket> {
    @Override
    default String getEntityName() {
        return "Ticket";
    }
}