package com.example.marketplace.security;

import com.example.marketplace.model.User;
import com.example.marketplace.model.enums.Roles;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;
    @Override
    public Collection<? extends GrantedAuthority>getAuthorities() {
        return user.getRoles();
    }

    public boolean isAdmin(){
        return user.getRoles().contains(Roles.ADMIN_ROLE);
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
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
