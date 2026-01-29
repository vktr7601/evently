package com.company.ticket_service.account;

import org.springframework.security.core.GrantedAuthority;

public enum AccountRoles implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");
    private final String authority;

    AccountRoles(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
