package com.company.ticket_service.accountPreferences;

import com.company.ticket_service.account.Account;
import com.company.ticket_service.classification.Classification;
import com.company.ticket_service.core.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "userPreferences")
public class AccountPreferences extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "classification")
    private Classification classification;

    @Column(name = "active")
    private boolean isActive;

    @Override
    public void onCreate() {
        super.onCreate();
        isActive = true;
    }
}
