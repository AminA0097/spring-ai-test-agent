package com.webapp.arvand.coreback.Jwt;

import com.webapp.arvand.coreback.Guava.GuavaCache;
import com.webapp.arvand.coreback.Guava.GuavaService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private GuavaService guavaService;
    public JwtAuthFilter(JwtService jwtService, GuavaService guavaService) {
        this.jwtService = jwtService;
        this.guavaService = guavaService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String token = extractCookie(request);
        if (token != null) {
            try {
                Claims allClaim = jwtService.extractAllClaims(token);
                if (allClaim != null) {
                    GuavaCache guavaCache =
                            guavaService.checkExistAncCompare(allClaim);
                    if(guavaCache != null) {
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        for(String roleName : guavaCache.getRoles()) {
                            authorities.add(new SimpleGrantedAuthority(roleName));
                        }
                        JwtAuthentication jwtAuthentication =
                                new JwtAuthentication(authorities,guavaCache,true);
                        SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
                    }
                }
            }

            catch (Exception e){
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
            }
            filterChain.doFilter(request,response);
        }


    }

    private String extractCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
