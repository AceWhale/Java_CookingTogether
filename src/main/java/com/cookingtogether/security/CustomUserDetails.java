package com.cookingtogether.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    // Конструктор
    public CustomUserDetails(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Логика проверки истечения срока действия аккаунта
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Логика проверки блокировки аккаунта
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Логика проверки истечения срока действия учетных данных
    }

    @Override
    public boolean isEnabled() {
        return true; // Логика проверки, активен ли пользователь
    }
}
