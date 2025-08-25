package com.civicpark.utils;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.civicpark.entities.RtoOffice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RtoUserDetails implements UserDetails {
    private final RtoOffice rto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_RTO"));
    }

    @Override
    public String getPassword() {
        return rto.getPassword(); // stored encoded password
    }

    @Override
    public String getUsername() {
        return rto.getEmail(); // login with email
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
