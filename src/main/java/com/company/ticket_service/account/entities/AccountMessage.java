package com.company.ticket_service.account.entities;

import java.io.Serializable;
import java.util.List;

public record AccountMessage(
        long accountId,
        List<String> preferences
) {
}
