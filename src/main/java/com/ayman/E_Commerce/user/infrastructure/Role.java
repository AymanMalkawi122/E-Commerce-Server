package com.ayman.E_Commerce.user.infrastructure;

import com.ayman.E_Commerce.core.Constants;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;
    @Override
    public String getAuthority() {
        return switch (this) {
            case ROLE_USER -> Constants.USER_ROLE_NAME;
            case ROLE_ADMIN -> Constants.ADMIN_ROLE_NAME;
        };
    }
}