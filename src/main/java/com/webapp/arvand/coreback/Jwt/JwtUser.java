package com.webapp.arvand.coreback.Jwt;

import java.util.Collections;
import java.util.Set;

public final class JwtUser {
    private final Long userId;
    private final String userName;
    private final Set<String> roles;
    public JwtUser(Long userId, String userName, Set<String> roles) {
        this.userId = userId;
        this.userName = userName;
        this.roles = Collections.unmodifiableSet(roles);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
