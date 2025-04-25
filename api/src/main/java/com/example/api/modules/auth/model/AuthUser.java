package com.example.api.modules.auth.model;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.api.enums.EAuthorityKeys;
import com.example.api.modules.users.persistence.UserEntity;

public class AuthUser implements UserDetails {
    private final UserEntity user;
    private final Set<SimpleGrantedAuthority> authorities;

    public AuthUser(UserEntity user) {
        this.user = user;
        this.authorities = buildAuthorities(user);
    }

    private Set<SimpleGrantedAuthority> buildAuthorities(UserEntity user) {
        Set<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(EAuthorityKeys.ROLE.getName() + role.name()))
                .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> permissionAuthorities = user.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(EAuthorityKeys.PERMISSION.getName() + permission.name()))
                .collect(Collectors.toSet());

        authorities.addAll(permissionAuthorities);
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
