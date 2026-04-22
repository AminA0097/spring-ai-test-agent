package com.webapp.arvand.coreback.Jwt;

import com.webapp.arvand.coreback.Guava.GuavaCache;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final GuavaCache guavaCache;

    public JwtAuthentication(Collection<? extends GrantedAuthority> authorities,
                             GuavaCache guavaCache,
                             boolean authenticated) {
        super(authorities);
        this.guavaCache = guavaCache;
        super.setAuthenticated(authenticated);
    }
    public static JwtAuthentication jwtAuthentication(
            GuavaCache guavaCache,
            Collection<? extends GrantedAuthority> authorities,
            boolean authenticated
    ){
        return new JwtAuthentication(authorities, guavaCache, authenticated);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Use JwtAuthentication instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public  Object getCredentials() {
        return null;
    }

    @Override
    public  Object getPrincipal() {
        return guavaCache;
    }
}
