package com.company.ticket_service.notifications;

import com.company.ticket_service.account.Account;
import com.company.ticket_service.core.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "read")
    private boolean isRead;

    @Override
    public void onCreate() {
        super.onCreate();
        isRead = false;
    }
}
