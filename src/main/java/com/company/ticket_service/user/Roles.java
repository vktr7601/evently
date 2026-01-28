package com.company.ticket_service.user;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");
    private String authority;

    Roles(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}