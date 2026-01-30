package com.company.ticket_service.authentication.models;

import com.company.ticket_service.account.AccountRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class AuthUser implements UserDetails {
    private long id;
    private String email;
    private String password;
    private AccountRoles roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(roles);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
