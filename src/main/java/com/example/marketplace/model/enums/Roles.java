package com.example.marketplace.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    USER_ROLE,ADMIN_ROLE;
    @Override
    public String getAuthority() {
        return name();
    }
}
