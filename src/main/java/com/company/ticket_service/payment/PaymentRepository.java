package com.company.ticket_service.payment;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends BaseRepository<Payment> {
}